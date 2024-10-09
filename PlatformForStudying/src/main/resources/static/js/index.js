document.getElementById("logoutBtn").addEventListener("click", function() {
    let xhr = new XMLHttpRequest();
    xhr.open("POST", "/logout", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            window.location.href = "/login";
        }
    };
    xhr.send();
});