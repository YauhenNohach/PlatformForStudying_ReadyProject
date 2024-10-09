function getTestsForUser() {
    fetch('http://localhost:8081/test/all')
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Error in fetching data.');
            }
        })
        .then(data => {
            displayTests(data);
        })
        .catch(error => console.error('Error:', error));
}

function displayTests(tests) {
    const userSection = document.getElementById('student-section');

    if (userSection === null) {
        console.log("this user is an admin");
    } else {
        userSection.innerHTML = '';
        tests.forEach(test => {
            const startTimeFormatted = test.startTime ? moment(test.startTime).format('YYYY-MM-DDTHH:mm') : 'Not set';
            const endTimeFormatted = test.endTime ? moment(test.endTime).format('YYYY-MM-DDTHH:mm') : 'Not set';

            const testElement = document.createElement('div');
            testElement.classList.add('test-card');
            testElement.innerHTML = `
                <div class="test-header" onclick="toggleTestDetails(this)">
                    <h3>${test.testName}</h3>
                </div>
                <div class="test-info" style="display: none;">
                    <p style="display: none;">ID: ${test.id}</p>
                    <p>Start Time: ${startTimeFormatted}</p>
                    <p>End Time: ${endTimeFormatted}</p>
                    <p>Duration: ${test.duration} minutes</p>
                    <p>Created By: ${test.createdBy}</p>
                </div>
                <button class="start-test-btn" onclick="startTest(${test.id})">Start Test</button>
            `;

            userSection.appendChild(testElement);
        });
    }
}

function toggleTestDetails(testHeader) {
    const testInfo = testHeader.nextElementSibling;
    testInfo.style.display = testInfo.style.display === 'none' ? 'block' : 'none';
}

function startTest(testId) {
    window.location.href = `http://localhost:8081/questions?testId=${testId}`;
}

document.addEventListener('DOMContentLoaded', getTestsForUser);

function backToProfile() {
    window.location.href = '/profile';
}