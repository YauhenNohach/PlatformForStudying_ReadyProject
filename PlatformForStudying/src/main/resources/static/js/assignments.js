document.addEventListener("DOMContentLoaded", function () {
    function fetchAssignments() {
        fetch("http://localhost:8081/assignments/get")
            .then(response => response.json())
            .then(assignments => {displayAssignments(assignments);})
            .catch(error => console.error("Error fetching assignments:", error));
    }

    function displayAssignments(assignments) {
        const testList = document.getElementById("testList");

        if (!testList) {
            console.error("Error: Element with id 'testList' not found.");
            return;
        }

        testList.innerHTML = "";

        assignments.forEach(assignment => {
            const listItem = document.createElement("li");
            listItem.classList.add("assignment-item");
            listItem.innerHTML = `
            <div class="assignment-header">
                ${assignment.test.testName} - Rating: ${assignment.rating} - Status: ${assignment.status}
            </div>
            <div class="assignment-details" style="display:none;">
                <h3>Questions:</h3>
                <ol>
                    ${assignment.test.questions.map((question, index) => {
                const userAnswer = assignment.userAnswers && assignment.userAnswers[index] ? assignment.userAnswers[index] : "Not answered";
                return `
                            <li>
                                <p>Question: ${question.text}</p>
                                <ol class="answer-list">
                                    ${question.possibleAnswers.map((answer) => `<li>${answer}</li>`).join('')}
                                </ol>
                                <p>User Answer: ${userAnswer}</p>
                            </li>`;
            }).join("")}
                </ol>
            </div>
        `;

            listItem.addEventListener("click", function () {
                const details = listItem.querySelector('.assignment-details');
                if (details) {
                    details.style.display = details.style.display === 'none' ? 'block' : 'none';
                }
            });

            testList.appendChild(listItem);
        });
    }

    window.backToProfile = function () {
        document.getElementById("assignContent").style.display = "none";
    };

    fetchAssignments();
});

function backToProfile() {
    window.location.href = '/profile';
}