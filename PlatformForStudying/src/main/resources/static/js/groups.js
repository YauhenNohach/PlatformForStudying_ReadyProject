document.addEventListener('DOMContentLoaded', function () {
    const groupList = document.getElementById('groupList');
    const groupStudentsContainer = document.getElementById('groupStudents');
    const studentsContainer = document.getElementById('students');
    const showAllButton = document.getElementById('showAllButton');
    const searchFirstNameInput = document.getElementById('searchFirstName');
    const searchLastNameInput = document.getElementById('searchLastName');
    const searchButton = document.getElementById('searchButton');

    let selectedGroupId = null;

    function toggleStudents(groupId) {
        if (selectedGroupId === groupId) {
            studentsContainer.innerHTML = '';
            groupStudentsContainer.style.display = 'none';
            selectedGroupId = null;
        } else {
            fetch(`http://localhost:8081/group/${groupId}`)
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! Status: ${response.status}`);
                    }
                    return response.json();
                })
                .then(data => {
                    studentsContainer.innerHTML = '';

                    if (Array.isArray(data.result.students)) {
                        data.result.students.forEach((student, index) => {
                            const studentItem = document.createElement('div');
                            studentItem.innerHTML = `<span>${index + 1}.</span>${student.firstName} ${student.lastName}`;

                            const removeButton = document.createElement('button');
                            removeButton.textContent = 'remove from group';
                            removeButton.addEventListener('click', () => removeStudentFromGroup(selectedGroupId, student.id));
                            studentItem.appendChild(removeButton);

                            studentsContainer.appendChild(studentItem);
                        });
                    } else {
                        console.error('Invalid response format for students:', data);
                    }

                    groupStudentsContainer.style.display = 'block';
                    selectedGroupId = groupId;
                })
                .catch(error => console.error('Error fetching students:', error));
        }
    }

    function removeStudentFromGroup(groupId, studentId) {
        fetch(`http://localhost:8081/teacher/remove?groupId=${groupId}&studentId=${studentId}`, {
            method: 'DELETE',
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }

                const contentType = response.headers.get('content-type');
                if (contentType && contentType.includes('application/json')) {
                    return response.json();
                } else {
                    return {message: 'Success'};
                }
            })
            .then(data => {
                alert('Student removed from group successfully');
                console.log('Student removed from group successfully:', data);
                toggleStudents(groupId);
            })
            .catch(error => console.error('Error removing student from group:', error));
    }

    function searchStudents(query) {
        fetch(`http://localhost:8081/teacher/search?firstname=${encodeURIComponent(query.firstname)}&lastname=${encodeURIComponent(query.lastname)}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        })
            .then(response => response.json())
            .then(data => {
                studentsContainer.innerHTML = '';
                if (data.result && !data.error) {
                    const students = Array.isArray(data.result.students) ? data.result.students : [data.result];

                    students.forEach((student, index) => {
                        const studentItem = document.createElement('div');
                        studentItem.innerHTML = `<span>${index + 1}.</span>${student.firstName} ${student.lastName}`;

                        const addButton = document.createElement('button');
                        addButton.textContent = 'add in group';
                        addButton.addEventListener('click', () => addStudentToGroup(selectedGroupId, student.id));
                        studentItem.appendChild(addButton);

                        studentsContainer.appendChild(studentItem);
                    });
                } else {
                    alert('student not found');
                    console.error('Invalid response format for students:', data);
                }
            })
            .catch(error => console.error('Error searching students:', error));
    }

    searchButton.addEventListener('click', () => {
        const searchQuery = {
            firstname: searchFirstNameInput.value.trim(),
            lastname: searchLastNameInput.value.trim(),
        };

        if (searchQuery.firstname !== '' || searchQuery.lastname !== '') {
            searchStudents(searchQuery);
        }
    });

    function addStudentToGroup(groupId, studentId) {
        fetch(`http://localhost:8081/group/${groupId}/addStudent/${studentId}`, {
            method: 'POST',
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                alert('Student added to group successfully');
                console.log('Student added to group successfully:', data);
            })
            .catch(error => console.error('Error adding student to group:', error));
    }

    function showAllStudents() {
        fetch('http://localhost:8081/students/all')
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                studentsContainer.innerHTML = '';
                data.result.forEach((student, index) => {
                    const studentItem = document.createElement('div');
                    studentItem.innerHTML = `<span>${index + 1}.</span>${student.firstName} ${student.lastName}`;

                    const addButton = document.createElement('button');
                    addButton.textContent = 'add in group';
                    addButton.addEventListener('click', () => addStudentToGroup(selectedGroupId, student.id));
                    studentItem.appendChild(addButton);

                    studentsContainer.appendChild(studentItem);
                });
            })
            .catch(error => console.error('Error fetching all students:', error));
    }

    searchButton.addEventListener('click', () => {
        const searchQuery = {
            firstname: searchFirstNameInput.value.trim(),
            lastname: searchLastNameInput.value.trim(),
        };

        if (searchQuery.firstname !== '' || searchQuery.lastname !== '') {
            searchStudents(searchQuery);
        }
    });

    showAllButton.addEventListener('click', showAllStudents);

    fetch('http://localhost:8081/group/all')
        .then(response => response.json())
        .then(data => {
            data.result.forEach(group => {
                const listItem = document.createElement('li');
                listItem.textContent = group.name;
                listItem.addEventListener('click', () => toggleStudents(group.id));
                groupList.appendChild(listItem);
            });
        })
        .catch(error => console.error('Error fetching groups:', error));
});
