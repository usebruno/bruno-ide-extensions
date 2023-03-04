const vscode = require('vscode');

function activate(context) {
	console.log('Congratulations, your extension "bruno" is now active!');

  let disposable = vscode.commands.registerCommand('bruno.sayHello', () => {
    vscode.window.showInformationMessage('Hello World!');
  });

  context.subscriptions.push(disposable);
}

module.exports = {
  activate
};
