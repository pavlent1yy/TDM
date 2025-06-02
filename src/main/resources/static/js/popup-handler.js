function openNewTaskPopup() {
    document.getElementById("newTask").style.display = "flex";
    const now = new Date();
    document.getElementById("currentDate").innerText = now.toLocaleDateString("ru-RU");
}

function openDeleteConfirmationById(id) {
    document.getElementById(id).style.display = "flex";
}

function closePopup() {
    document.querySelectorAll('.popup-overlay').forEach(p => p.style.display = 'none');
}

function closePopupById(popup) {
    document.getElementById(popup).style.display = "none";
}

function openTaskDeletePopup(userId, taskId) {
    const form = document.getElementById("taskDeleteForm");
    form.action = `/ui/users/${userId}/tasks/delete/${taskId}`;
    document.getElementById("deleteTaskId").value = taskId;
    document.getElementById("taskDeleteConfirmation").style.display = "flex";
}


function openEditTask(taskId, taskTitle, taskDescription, isCompleted, userId) {
    const popup = document.getElementById("taskEdit");
    document.getElementById("editTaskId").value = taskId;
    popup.querySelector('input[name="title"]').value = taskTitle;
    popup.querySelector('textarea[name="description"]').value = taskDescription;
    popup.querySelector('input[name="completed"]').checked = isCompleted;

    popup.querySelector('form').action = `/ui/users/${userId}/tasks/edit/${taskId}`;
    popup.style.display = "flex";
}
