/**
 * Function called when submitting a new test case.
 */
function handleCreateTestCaseDlgSubmit(xhr, status, args) {
	handleDlgSubmit(xhr, status, args, createTestCaseDlg);
}

/**
 * Function called when editing a test case
 * 
 * @param xhr
 * @param status
 * @param args
 */
function handleEditTestCaseDlgSubmit(xhr, status, args) {
	handleDlgSubmit(xhr, status, args, editTestCaseDlg);
}

/**
 * Function called when editing a test objective
 * 
 * @param xhr
 * @param status
 * @param args
 */
function handleEditTestObjectiveDlgSubmit(xhr, status, args) {
	handleDlgSubmit(xhr, status, args, editTestObjectiveDlg);
}

/**
 * Function called when editing a test plan
 * 
 * @param xhr
 * @param status
 * @param args
 */
function handleEditTestPlanDlgSubmit(xhr, status, args) {
	handleDlgSubmit(xhr, status, args, editTestPlanDlg);
}

/**
 * to handle dialog submit. This will close dialog when actionSuccess argument
 * is true
 * 
 * @param xhr
 * @param status
 * @param args
 * @param componentId
 */
function handleDlgSubmit(xhr, status, args, componentId) {
	if (!args.actionSuccess) {
		componentId.jq.effect("shake", {
			times : 2
		}, 100);
	} else {
		componentId.hide();
	}

}

/**
 * Helper function used when doing a on keyup search
 */
var delay = (function() {
	var timer = 0;
	return function(callback, ms) {
		clearTimeout(timer);
		timer = setTimeout(callback, ms);
	};
})();

/**
 * Called when doing a keyup delay.
 */
function ajaxSearchOthersDelay() {
	delay(function() {
		searchCommand();
	}, 1000);
}

function delaySearchCommandOnTestPlan() {
	delay(function() {
		searchCommandOnTestPlan();
	}, 1000);
}

/**
 * Show or hide an element with the specified ID
 */
function toggle(id) {
	$("#" + id).slideToggle("slow");
}

/**
 * add title for p:rowEditor
 */
function addTitleForRowEditor() {
	// add title for p:rowEditor
	rowEditor = $(".ui-row-editor");
	rowEditor.find("span.ui-icon-pencil").attr('title', 'Click to edit');
	rowEditor.find("span.ui-icon-check").attr('title', 'Save changes');
	rowEditor.find("span.ui-icon-close").attr('title', 'Cancel editing');
}

/**
 * Listener for DOM updated
 */
$(document).bind("DOMSubtreeModified", function() {
	delay(function() {
		addTitleForRowEditor();
	}, 1000);
});
