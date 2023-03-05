const vscode = require('vscode');
const path = require('path');

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
      'Bruno',
      vscode.ViewColumn.Beside,
      {
        enableScripts: true,
        retainContextWhenHidden: true,
        localResourceRoots: [vscode.Uri.file(path.join(context.extensionPath, 'out'))]
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

    const scriptUri = this.panel.webview.asWebviewUri(
      vscode.Uri.joinPath(this.context.extensionUri, 'out', 'bruno', 'index.js')
    );

    try {
      const json = JSON.parse(text);
      const prettyJson = JSON.stringify(json, null, 2);
      this.panel.webview.html = `<!DOCTYPE html>
        <html lang="en">
        <head>
          <meta charset="UTF-8">
          <meta name="viewport" content="width=device-width, initial-scale=1.0">
          <meta http-equiv="Content-Security-Policy" content="default-src 'none'; img-src vscode-resource: https:; script-src 'unsafe-eval' 'unsafe-inline' vscode-resource: https:; style-src vscode-resource: 'unsafe-inline' https:;">
          <title>Bruno</title>
        </head>
        <body>
          <div id="root"></div>
          <script src="${scriptUri}"></script>
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
