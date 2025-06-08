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


function openEditTask(button) {
    const taskId = button.dataset.id;
    const taskTitle = button.dataset.title;
    const taskDescription = button.dataset.description;
    const isCompleted = button.dataset.completed === "true";
    const userId = button.dataset.userid;
    const createdAt = button.dataset.createdat;

    const popup = document.getElementById("taskEdit");

    popup.querySelector('input[name="taskId"]').value = taskId;
    popup.querySelector('input[name="userId"]').value = userId;
    popup.querySelector('input[name="createdAt"]').value = createdAt;

    popup.querySelector('input[name="title"]').value = taskTitle;
    popup.querySelector('textarea[name="description"]').value = taskDescription;
    popup.querySelector('input[name="completed"]').checked = isCompleted;

    popup.querySelector('form').action = `/ui/users/${userId}/tasks/edit/${taskId}`;
    popup.style.display = "flex";
}



