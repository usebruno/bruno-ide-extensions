const vscode = require('vscode');
const BrunoPanel = require('./panel');

function activate(context) {
	console.log('Congratulations, your extension "bruno" is now active!');

  let disposable = vscode.commands.registerCommand('bruno.sayHello', () => {
    vscode.window.showInformationMessage('Hello World!');
  });

  context.subscriptions.push(disposable);

  context.subscriptions.push(
    vscode.commands.registerCommand('bruno.runRequest', () => {
      BrunoPanel.createOrShow(context);
    })
  );

  const brunoPanelProvider = {
    provideWebviewPanel: (panel, _context, _token) => {
      if (panel.viewType !== 'Bruno') {
        return;
      }

      const brunoPanel = new BrunoPanel(panel, context);
      brunoPanel.update();

      context.subscriptions.push(
        vscode.window.onDidChangeActiveTextEditor((editor) => {
          if (editor) {
            const content = editor.document.getText();
            brunoPanel.update(content);
          }
        }
      ));
    },
  };

  context.subscriptions.push(
    vscode.window.registerWebviewPanelSerializer('BrunoPanel', brunoPanelProvider)
  );
}

module.exports = {
  activate
};
