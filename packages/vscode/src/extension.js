const vscode = require('vscode');
const BrunoPanel = require('./panel');

function activate(context) {
	console.log('Congratulations, your extension "bruno" is now active!');

  context.subscriptions.push(
    vscode.commands.registerCommand('extension.runBrunoRequest', () => {
      BrunoPanel.createOrShow(context);
    })
  );

  const brunoPanelProvider = {
    provideWebviewPanel: (panel, _context, _token) => {
      if (panel.viewType !== 'Bruno') {
        return;
      }

      const brunoPanel = new BrunoPanel(panel, context);
    },
  };

  context.subscriptions.push(
    vscode.window.registerWebviewPanelSerializer('BrunoPanel', brunoPanelProvider)
  );
}

module.exports = {
  activate
};
