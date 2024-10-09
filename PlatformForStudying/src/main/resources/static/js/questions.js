let timerInterval;

async function fetchTestDetails(testId) {
    try {
        const response = await fetch(`http://localhost:8081/questions/${testId}`);
        if (!response.ok) {
            throw new Error(response.statusText);
        }

        const apiResponse = await response.json();

        if (apiResponse.error) {
            handleErrorResponse(apiResponse.errorMessage);
        } else {
            displayQuestions(apiResponse.result);
     const storedRemainingTime = localStorage.getItem(`remainingTime_${testId}`);

            if (storedRemainingTime) {
                startTimer(parseInt(storedRemainingTime, 10), apiResponse.result.endTime, testId);
            } else {
                 startTimer(apiResponse.result.duration * 60, apiResponse.result.endTime, testId);
            }
        }
    } catch (error) {
        handleFetchError(error);
    }
}

async function startTimer(durationInSeconds, endTime, testId) {
    const timerElement = document.getElementById('timer');

    function updateTimerDisplay() {
        const minutes = Math.floor(durationInSeconds / 60);
        const seconds = durationInSeconds % 60;
        timerElement.textContent = `${minutes}:${seconds < 10 ? '0' : ''}${seconds}`;
    }

    updateTimerDisplay();

    timerInterval = setInterval(function () {
        const now = new Date();
        const endDateTime = new Date(endTime);

        if (now > endDateTime || durationInSeconds <= 0) {
            clearInterval(timerInterval);
            submitTest();

            localStorage.removeItem(`remainingTime_${testId}`);
        } else {
            durationInSeconds--;
            updateTimerDisplay();

            localStorage.setItem(`remainingTime_${testId}`, durationInSeconds);
        }
    }, 1000);

    window.addEventListener('beforeunload', function () {
        localStorage.setItem(`remainingTime_${testId}`, durationInSeconds);
    });

    document.addEventListener('visibilitychange', function () {
        if (document.visibilityState === 'visible') {
            startTimer(durationInSeconds, endTime, testId);
        }
    });

    window.addEventListener('pageshow', function (event) {
        if (event.persisted) {
              startTimer(durationInSeconds, endTime, testId);
        }
    });
}

let redirectionHandled = false;

function handleErrorResponse(errorMessage) {
    if (!redirectionHandled) {
        alert(errorMessage);
        redirectionHandled = true;
        window.location.href = '/profile';
    }
}

function handleFetchError(error) {
    if (error instanceof TypeError) {
        console.error('Network error:', error);
        alert('Network error: Unable to reach the server');
    } else {
        console.error('Server error:', error);
        alert('Server error: Unable to process the request');
    }
}

function displayQuestions(test) {
    const questionsSection = document.getElementById('questions-section');

    questionsSection.innerHTML = '';

    test.questions.forEach(question => {
        const questionElement = document.createElement('div');
        questionElement.classList.add('question-card');

        const questionText = document.createElement('p');
        questionText.textContent = question.text;
        questionElement.appendChild(questionText);

        const answersList = document.createElement('ol');
        question.possibleAnswers.forEach(answer => {
            const answerItem = document.createElement('li');
            answerItem.textContent = answer;
            answersList.appendChild(answerItem);
        });
        questionElement.appendChild(answersList);

        const answerInput = document.createElement('input');
        answerInput.type = 'text';
        answerInput.placeholder = 'Enter your answer';
        answerInput.classList.add('answer-input');
        questionElement.appendChild(answerInput);

        questionsSection.appendChild(questionElement);
    });
}

function getTestIdFromUrl() {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('testId');
}

document.addEventListener('DOMContentLoaded', function () {
    const testId = getTestIdFromUrl();

    fetchTestDetails(testId)
        .then(() => {
            const submitTestButton = document.getElementById('submit-test-btn');
            submitTestButton.addEventListener('click', submitTest);

            window.addEventListener('beforeunload', function () {
                submitTest();
            });
        })
        .catch(error => {
             console.error('Error fetching test details:', error);
        });
});


function submitTest() {
    clearInterval(timerInterval);
    const answerInputs = document.querySelectorAll('.answer-input');
    const userAnswers = [];

    answerInputs.forEach(input => {
        const selectedAnswer = input.value.trim();
        userAnswers.push(selectedAnswer);
    });

    const testId = getTestIdFromUrl();
    const apiUrl = `http://localhost:8081/test/pass/${testId}`;
    fetch(apiUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(userAnswers),
    })
        .then(response => response.json())
        .then(data => {
            alert("test passed");
            window.location.href = '/profile';
            if (!data.error) {
                window.location.href = '/profile';
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
}