function addOption() {
    const answerOptionsContainer = document.getElementById('answerOptionsContainer');

    const optionRow = document.createElement('div');
    optionRow.className = 'optionRow';

    const input = document.createElement('input');
    input.type = 'text';
    input.className = 'answerOption';
    input.name = 'answerOptions';
    input.placeholder = 'Option';
    input.required = true;

    const removeButton = document.createElement('button');
    removeButton.type = 'button';
    removeButton.textContent = 'Remove';
    removeButton.addEventListener('click', function() {
        removeOption(this);
    });

    optionRow.appendChild(input);
    optionRow.appendChild(removeButton);
    answerOptionsContainer.appendChild(optionRow);
}

function removeOption(button) {
    const optionRow = button.parentNode;
    const answerOptionsContainer = document.getElementById('answerOptionsContainer');
    answerOptionsContainer.removeChild(optionRow);
}

function showQuestionForm() {
    const questionTypeSelector = document.getElementById('selectQuestionType');
    const questionForm = document.getElementById('questionForm');
    const optionsContainer = document.getElementById('optionsContainer');
    const rightAnswerContainer = document.getElementById('rightAnswerContainer');

    if (questionTypeSelector.value === 'SELECT_TYPE') {
        optionsContainer.style.display = 'none';
        rightAnswerContainer.style.display = 'none';
        questionForm.style.display = 'none';
    } else if (questionTypeSelector.value === 'OPTION_QUESTION') {
        optionsContainer.style.display = 'block';
        rightAnswerContainer.style.display = 'block';
        questionForm.style.display = 'block';
    } else {
        optionsContainer.style.display = 'none';
        rightAnswerContainer.style.display = 'block';
        questionForm.style.display = 'block';
    }
}

showQuestionForm();

function addQuestion() {
    const questionText = document.getElementById('questionText').value;
    const questionType = document.getElementById('selectQuestionType').value;
    const rightAnswer = document.getElementById('rightAnswer').value.trim();

    let question = { text: questionText, type: questionType, rightAnswer: rightAnswer, possibleAnswers: [] };

    if (questionType === 'OPTION_QUESTION') {
        const answerOptions = document.querySelectorAll('.answerOption');
        const options = Array.from(answerOptions).map(option => option.value.trim());

        question.possibleAnswers = options;
    }

    questions.push(question);

    displayQuestions();
    resetForm();
}

function removeQuestion(index) {
    questions.splice(index, 1);
    displayQuestions();
}

function displayQuestions() {
    const questionsContainer = document.getElementById('questionsContainer');
    questionsContainer.innerHTML = '';

    questions.forEach((question, index) => {
        const questionDiv = document.createElement('div');
        questionDiv.className = 'questionContainer';

        const questionTextDiv = document.createElement('div');
        questionTextDiv.innerHTML = `<strong>Question ${index + 1}:</strong> ${question.text}`;
        questionDiv.appendChild(questionTextDiv);

        if (question.type === 'OPTION_QUESTION') {
            const optionsList = document.createElement('ol');
            question.possibleAnswers.forEach((option, optionIndex) => {
                const optionItem = document.createElement('li');
                optionItem.textContent = `${optionIndex + 1}. ${option}`;
                optionsList.appendChild(optionItem);
            });
            questionDiv.appendChild(optionsList);

            const rightAnswerDiv = document.createElement('div');
            rightAnswerDiv.innerHTML = `<strong>Right Answer:</strong> ${question.rightAnswer}`;
            questionDiv.appendChild(rightAnswerDiv);
        }

        if (question.type === 'INPUT_QUESTION' && question.rightAnswer) {
            const rightAnswerDiv = document.createElement('div');
            rightAnswerDiv.innerHTML = `<strong>Right Answer:</strong> ${question.rightAnswer}`;
            questionDiv.appendChild(rightAnswerDiv);
        }

        const removeButton = document.createElement('button');
        removeButton.type = 'button';
        removeButton.className = 'removeButton';
        removeButton.textContent = 'Remove Question';
        removeButton.addEventListener('click', function() {
            removeQuestion(index);
        });
        questionDiv.appendChild(removeButton);

        questionsContainer.appendChild(questionDiv);
    });
}


function resetForm() {
    document.getElementById('questionText').value = '';
    document.getElementById('selectQuestionType').value = 'SELECT_TYPE';
    document.getElementById('rightAnswer').value = '';

    const answerOptionsContainer = document.getElementById('answerOptionsContainer');
    answerOptionsContainer.innerHTML = '';

    const optionsContainer = document.getElementById('optionsContainer');
    optionsContainer.style.display = 'none';

    const rightAnswerContainer = document.getElementById('rightAnswerContainer');
    rightAnswerContainer.style.display = 'none';
    showQuestionForm();
}
async function createTest() {
    const testName = document.getElementById('testName').value.trim();

    const testData = questions;

    try {
        const response = await fetch(`http://localhost:8081/test/create?testName=${testName}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(testData),
        });

        if (!response.ok) {
            throw new Error('Failed to create test');
        }

        const result = await response.json();
        alert('Test created successfully');
        console.log('Test created successfully:', result);

        questions = [];

        displayQuestions();
        resetForm();
    } catch (error) {
        console.error('Error creating test:', error);
    }
}
function backToProfile() {
    window.location.href = '/profile';
}

let questions = [];

showQuestionForm();