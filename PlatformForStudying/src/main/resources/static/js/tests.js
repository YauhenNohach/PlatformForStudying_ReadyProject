async function getTests() {
    const response = await fetch('http://localhost:8081/test/allWithDetails');
    const tests = await response.json();

    const testList = document.getElementById('testList');
    testList.innerHTML = '';

    tests.forEach(test => {
        const listItem = document.createElement('li');

        const testLink = document.createElement('a');
        testLink.href = `javascript:void(0);`;
        testLink.textContent = test.testName;
        testLink.onclick = () => showTestContent(test);
        listItem.appendChild(testLink);

        const startTimeFormatted = test.startTime ? moment(test.startTime).format('YYYY-MM-DDTHH:mm') : 'Not set';
        const endTimeFormatted = test.endTime ? moment(test.endTime).format('YYYY-MM-DDTHH:mm') : 'Not set';

        const timeContainer = document.createElement('div');
        timeContainer.textContent = `Start Time: ${startTimeFormatted || 'Not set'},
         End Time: ${endTimeFormatted || 'Not set'},
         Duration: ${test.duration ? test.duration : 'Not Set'}`;
        listItem.appendChild(timeContainer);

        const setTimeButton = document.createElement('button');
        setTimeButton.textContent = 'Set Time';
        setTimeButton.addEventListener('click', function () {
            showTestTimeForm(test);
        });
        listItem.appendChild(setTimeButton);

        const testTimeFormContainer = document.createElement('div');
        testTimeFormContainer.style.display = 'none';
        testTimeFormContainer.id = `testTimeFormContainer-${test.id}`;

        listItem.appendChild(testTimeFormContainer);

        const startTimeInput = document.createElement('input');
        startTimeInput.type = 'datetime-local';
        startTimeInput.id = `startTime-${test.id}`;
        testTimeFormContainer.appendChild(startTimeInput);

        const endTimeInput = document.createElement('input');
        endTimeInput.type = 'datetime-local';
        endTimeInput.id = `endTime-${test.id}`;
        testTimeFormContainer.appendChild(endTimeInput);

        const durationInput = document.createElement('input');
        durationInput.type = 'number';
        durationInput.id = `duration-${test.id}`;
        testTimeFormContainer.appendChild(durationInput);

        const setTimeButtonInsideForm = document.createElement('button');
        setTimeButtonInsideForm.textContent = 'Set Time';
        setTimeButtonInsideForm.addEventListener('click', function () {
            setTestTime(test);
        });
        testTimeFormContainer.appendChild(setTimeButtonInsideForm);

        testList.appendChild(listItem);
    });

}

async function deleteTest(testId) {
    const response = await fetch(`http://localhost:8081/test/delete/${testId}`, {
        method: 'DELETE',
    });

    if (response.ok) {
        getTests();
    } else {
        const errorMessage = await response.text();
        alert('Failed to delete test');
        console.error(`Failed to delete test: ${errorMessage}`);
    }
}

async function showTestContent(test) {
    const testContent = document.getElementById('testContent');
    const testTimeFormContainer = document.getElementById(`testTimeFormContainer-${test.id}`);

    if (testContent.style.display === 'block') {
        testContent.style.display = 'none';
        testTimeFormContainer.style.display = 'none';
    } else {
        const testList = document.getElementById('testList');
        testList.innerHTML = '';

        const questionsList = document.getElementById('questionsList');
        questionsList.innerHTML = '';
        const assignTestButton = document.getElementById('assignTestButton');
        assignTestButton.style.display = 'block';
        assignTestButton.onclick = () => showGroupSelection(test);

        test.questions.forEach((question, index) => {
            const questionItem = document.createElement('div');
            questionItem.classList.add('question-container');

            questionItem.innerHTML = `
                <p class="question-text">${index + 1}. ${question.text}</p>
                <ol class="answer-list">
                    ${question.possibleAnswers.map((answer, answerIndex) => `<li>${answer}</li>`).join('')}
                </ol>
                <p class="right-answer">Right Answer: ${question.rightAnswer}</p>
                <hr class="divider">
            `;

            questionsList.appendChild(questionItem);
        });

        const deleteButton = document.createElement('button');
        deleteButton.textContent = 'Delete Test';
        deleteButton.addEventListener('click', function () {
            const confirmation = confirm('Are you sure you want to delete this test?');
            if (confirmation) {
                deleteTest(test.id);
            }
        });

        const listItem = document.createElement('li');

        const testLink = document.createElement('a');
        testLink.href = `javascript:void(0);`;
        testLink.textContent = test.testName;
        testLink.onclick = () => showTestContent(test);
        listItem.appendChild(testLink);

        const timeContainer = document.createElement('div');
        const startTimeFormatted = test.startTime ? moment(test.startTime).format('YYYY-MM-DDTHH:mm') : 'Not set';
        const endTimeFormatted = test.endTime ? moment(test.endTime).format('YYYY-MM-DDTHH:mm') : 'Not set';

        timeContainer.textContent = `Start Time: ${startTimeFormatted},
             End Time: ${endTimeFormatted},
             Duration: ${test.duration ? test.duration : 'Not Set'}`;
        listItem.appendChild(timeContainer);

        const setTimeButton = document.createElement('button');
        setTimeButton.textContent = 'Set Time';
        setTimeButton.addEventListener('click', function () {
            showTestTimeForm(test);
        });
        listItem.appendChild(setTimeButton);

        listItem.appendChild(deleteButton);

        const testTimeFormContainer = document.createElement('div');
        testTimeFormContainer.classList.add('test-time-form-container');
        testTimeFormContainer.style.display = 'none';
        testTimeFormContainer.id = `testTimeFormContainer-${test.id}`;

        listItem.appendChild(testTimeFormContainer);

        const startTimeInput = document.createElement('input');
        startTimeInput.type = 'datetime-local';
        startTimeInput.id = `startTime-${test.id}`;
        testTimeFormContainer.appendChild(startTimeInput);

        const endTimeInput = document.createElement('input');
        endTimeInput.type = 'datetime-local';
        endTimeInput.id = `endTime-${test.id}`;
        testTimeFormContainer.appendChild(endTimeInput);

        const durationInput = document.createElement('input');
        durationInput.type = 'number';
        durationInput.id = `duration-${test.id}`;
        testTimeFormContainer.appendChild(durationInput);

        const setTimeButtonInsideForm = document.createElement('button');
        setTimeButtonInsideForm.textContent = 'Set Time';
        setTimeButtonInsideForm.addEventListener('click', function () {
            setTestTime(test);
        });
        testTimeFormContainer.appendChild(setTimeButtonInsideForm);

        testList.appendChild(listItem);

        document.querySelectorAll('.test-content').forEach(content => {
            if (content !== testContent) {
                content.style.display = 'none';
            }
        });

        document.querySelectorAll('.test-time-form-container').forEach(formContainer => {
            formContainer.style.display = 'none';
        });

        testContent.style.display = 'block';
    }
}

function showGroupSelection(test) {
    const groupSelect = document.getElementById('groupSelect');
    groupSelect.style.display = 'block';
    loadGroupsIntoSelect();

    const assignTestButton = document.getElementById('assignTestButton');
    assignTestButton.style.display = 'none';

    const selectedTestId = test.id;

    const handleGroupSelection = async () => {
        const selectedGroupId = groupSelect.value;
        assignTestButton.disabled = !selectedGroupId;

        if (selectedGroupId) {
            try {
                const response = await fetch(`http://localhost:8081/teacher/assign?idTest=${selectedTestId}&idGroup=${selectedGroupId}`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                });

                const result = await response.json();

                if (!result.error) {
                    alert('Test assigned to group successfully!');
                } else {
                    alert('Error assigning test to group: ' + result.result);
                }
            } catch (error) {
                console.error('Error assigning test to group:', error);
            } finally {
                groupSelect.style.display = 'none';
                assignTestButton.style.display = 'block';
                groupSelect.removeEventListener('change', handleGroupSelection);
            }
        }
    };

    groupSelect.addEventListener('change', handleGroupSelection);
}

async function loadGroupsIntoSelect() {
    try {
        const response = await fetch('http://localhost:8081/group/all');
        const responseData = await response.json();

        if (Array.isArray(responseData.result)) {
            const groups = responseData.result;
            const groupSelect = document.getElementById('groupSelect');
            groupSelect.innerHTML = '';

            const defaultOption = document.createElement('option');
            defaultOption.value = '';
            defaultOption.textContent = 'Select Group';
            groupSelect.appendChild(defaultOption);

            groups.forEach(group => {
                const option = document.createElement('option');
                option.value = group.id;
                option.textContent = group.name;
                groupSelect.appendChild(option);
            });
        } else {
            console.error('Unexpected data format from the server:', responseData);
            alert('Error loading groups. Please try again.');
        }
    } catch (error) {
        console.error('Error loading groups:', error);
        alert('Error loading groups. Please try again.');
    }
}

function showTestTimeForm(test) {
    const testTimeFormContainer = document.getElementById(`testTimeFormContainer-${test.id}`);
    const formTitle = document.getElementById('formTitle');

    if (testTimeFormContainer.style.display === 'block') {
        testTimeFormContainer.style.display = 'none';
    } else {
        document.querySelectorAll('.test-time-form-container').forEach(formContainer => {
            formContainer.style.display = 'none';
        });

        testTimeFormContainer.style.display = 'block';
        testTimeFormContainer.setAttribute('data-test-id', test.id);

        formTitle.textContent = `Set Test Time - ${test.testName}`;
    }
}

async function setTestTime(test) {
    const startTime = document.getElementById(`startTime-${test.id}`).value;
    const endTime = document.getElementById(`endTime-${test.id}`).value;
    const duration = document.getElementById(`duration-${test.id}`).value;

    const testTimeRequest = {
        idTest: test.id,
        startTime: startTime,
        endTime: endTime,
        duration: duration
    };
    console.log('Test time request:', testTimeRequest);

    saveTestTime(testTimeRequest);
}


async function saveTestTime(testTimeRequest) {
    const response = await fetch('http://localhost:8081/test/time', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(testTimeRequest),
    });

    const result = await response.json();
    alert('Test time saved');
    console.log('Test time saved:', result);
}

window.onload = getTests;

function backToProfile() {
    window.location.href = '/profile';
}

function backToAllTests() {
    window.location.href = '/tests';
}