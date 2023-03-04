const vscode = require('vscode');

class BrunoPanel {
  /**
   * @param {vscode.ExtensionContext} context
   */
  static createOrShow(context) {
    const column = vscode.window.activeTextEditor
      ? vscode.window.activeTextEditor.viewColumn
      : vscode.ViewColumn.One;

    if (BrunoPanel.currentPanel) {
      BrunoPanel.currentPanel.panel.reveal(column);
      return;
    }

    const panel = vscode.window.createWebviewPanel(
      BrunoPanel.viewType,
      'Bruno Panel',
      column,
      {
        enableScripts: true,
        retainContextWhenHidden: true,
      }
    );

    BrunoPanel.currentPanel = new BrunoPanel(panel, context);
  }

  /**
   * @param {vscode.WebviewPanel} panel
   * @param {vscode.ExtensionContext} context
   */
  constructor(panel, context) {
    this.panel = panel;
    this.context = context;

    this.panel.onDidDispose(() => this.dispose(), null, context.subscriptions);

    this.update();
  }

  dispose() {
    BrunoPanel.currentPanel = undefined;
    this.panel.dispose();
  }

  update() {
    const editor = vscode.window.activeTextEditor;
    if (!editor || editor.document.languageId !== 'json') {
      this.panel.webview.html = `<html><body>No JSON document active.</body></html>`;
      return;
    }

    const document = editor.document;
    const text = document.getText();

    try {
      const json = JSON.parse(text);
      const prettyJson = JSON.stringify(json, null, 2);
      this.panel.webview.html = `
        <html>
          <body>
            <pre>${prettyJson}</pre>
          </body>
        </html>
      `;
    } catch (err) {
      this.panel.webview.html = `<html><body>Error parsing JSON: ${err.message}</body></html>`;
    }
  }
}

BrunoPanel.viewType = 'BrunoPanel';

/**
 * @type {BrunoPanel | undefined}
 */
BrunoPanel.currentPanel = undefined;

module.exports = BrunoPanel;
