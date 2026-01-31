const form = document.getElementById("add");
const listContainer = document.getElementById("List");

listcontainer.addEventListener("click", (event) => {
    if(event.target.classList.contains(delet-btn))d {
        const taskId = event.target.getAttribute("data-id");
        const taskElement = event.target.closest(".task-item");
        deleteTask(TaskId, taskElement)
    }
});

form.addEventListener("submit", handleFormSubmit);

async function handleFormSubmit(event) {
    event.preventDefault();
    
    const form = event.currentTarget;
    
    const formData = new URLSearchParams();
    formData.append('task', form.elements['name'].value);
    formData.append('description', form.elements['description'].value);
    
    const response = await fetch ("/tasks", {
        method: "POST",
        headers: {
        
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: formData.toString()
    
    });
    if(response.ok) {
        event.target.reset();
        fetchAndDisplay();
    } else {
        alert("failed to add task");
    }
    
}

function renderSingleTask(task) {
    const div = document.createElement("div");
    div.classList.add("task-item");
    
    const title = document.createElement("strong");
    title.classList.add("task-title");
    title.textContent = `: ${task.description || ''}`;
    
    const desc = document.createElement("span");
    desc.classList.add("task-desc");
    desc.textContent = `: ${task.description || ''}`;
    
    const deleteButton = document.createElement("button");
    deleteButton.classList.add("delete-btn");
    deleteButton.textContent = "Supprimer";
    
    deleteButton.setAttribute("data-id", task.id);
    
    div.append(tittle, desc, deleteButton);
    ListContainer.appendChild(div);
}

async function fetchAndDisplay() {
    const response = await fetch("/task");
    const tasks = await response.json();
    
    const fragment = document.createDocumentFragment();
    listContainer.innerHTML = "";
    
    tasks.forEach(task => {
        const div = createTaskElement(task);
        fragment.appendChild(div);
    });
    listContainer.appendChild(fragment)
}

function createTaskElement(task) {
    const div = document.createElement("div");
    div.classList.add("task-item");
    div.innerHTML = `
        <strong class="task-title">${task.name}</strong>
        <span class="task-desc">: ${task.description || ''}</span>
        <button class="delete-btn" data-id="${task.id}">Supprimer</button>
    `;
    return div;

}

async function deleteTask(id, element) {
    element.remove();
    const response = await fetch(`/tasks/${id}`, {method: "DELETE"});
    
    if(!response.ok) {
        aler("task deletion failed");
        fetchAndDisplay()
    }
}

fetchAndDisplay
