const { forOwn, each, extend, get } = require('lodash');
const FormData = require('form-data');
const axios = require('axios');
const prepareRequest = require('./prepare-request');
const interpolateVars = require('./interpolate-vars');
const { ScriptRuntime, TestRuntime, VarsRuntime, AssertRuntime } = require('@usebruno/js');

const runSingleRequest = async function (filename, bruJson, collectionPath, collectionVariables, envVariables) {
  try {
    const request = prepareRequest(bruJson.request);

    // make axios work in node using form data
    // reference: https://github.com/axios/axios/issues/1006#issuecomment-320165427
    if(request.headers && request.headers['content-type'] === 'multipart/form-data') {
      const form = new FormData();
      forOwn(request.data, (value, key) => {
        form.append(key, value);
      });
      extend(request.headers, form.getHeaders());
      request.data = form;
    }

    // run pre-request vars
    const preRequestVars = get(bruJson, 'request.vars.req');
    if(preRequestVars && preRequestVars.length) {
      const varsRuntime = new VarsRuntime();
      varsRuntime.runPreRequestVars(preRequestVars, request, envVariables, collectionVariables, collectionPath);
    }

    // run pre request script
    const requestScriptFile = get(bruJson, 'request.script.req');
    if(requestScriptFile && requestScriptFile.length) {
      const scriptRuntime = new ScriptRuntime();
      scriptRuntime.runRequestScript(requestScriptFile, request, envVariables, collectionVariables, collectionPath);
    }

    // interpolate variables inside request
    interpolateVars(request, envVariables, collectionVariables);

    // run request
    const timeStart = Date.now();
    const response = await axios(request);
    const timeEnd = Date.now();
    const duration = timeEnd - timeStart;

    // run post-response vars
    const postResponseVars = get(bruJson, 'request.vars.res');
    if(postResponseVars && postResponseVars.length) {
      const varsRuntime = new VarsRuntime();
      varsRuntime.runPostResponseVars(postResponseVars, request, response, envVariables, collectionVariables, collectionPath);
    }

    // run post response script
    const responseScriptFile = get(bruJson, 'request.script.res');
    if(responseScriptFile && responseScriptFile.length) {
      const scriptRuntime = new ScriptRuntime();
      scriptRuntime.runResponseScript(responseScriptFile, request, response, envVariables, collectionVariables, collectionPath);
    }

    // run assertions
    let assertionResults = [];
    const assertions = get(bruJson, 'request.assert');
    if(assertions && assertions.length) {
      const assertRuntime = new AssertRuntime();
      assertionResults = assertRuntime.runAssertions(assertions, request, response, envVariables, collectionVariables, collectionPath);
    }

    // run tests
    let testResults = [];
    const testFile = get(bruJson, 'request.tests');
    if(testFile && testFile.length) {
      const testRuntime = new TestRuntime();
      const result = testRuntime.runTests(testFile, request, response, envVariables, collectionVariables, collectionPath);
      testResults = get(result, 'results', []);
    }

    return {
      request: {
        method: request.method,
        url: request.url,
        headers: Object.entries(request.headers),
        data: request.data
      },
      assertionResults,
      testResults,
      response: {
        status: response.status,
        statusText: response.statusText,
        headers: Object.entries(response.headers),
        size: response.headers['content-length'] || 0,
        duration,
        data: response.data
      }
    };
  } catch (err) {
    return Promise.reject(err);
  }
};

module.exports = {
  runSingleRequest
};
