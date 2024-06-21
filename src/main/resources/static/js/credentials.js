const form = document.querySelector('dialog').querySelector('form');
const currentPassword = document.querySelector('#currentPassword');
const newPassword = document.querySelector('#newPassword');
const newPasswordConfirmation = document.querySelector('#newPasswordConfirm');
const submitButton = document.querySelector('#modal-button-confirm');
submitButton.addEventListener('click', () => {
    const old = currentPassword.value;
    const current = newPassword.value;
    const current2 = newPasswordConfirmation.value;

    const oldLabel = document.querySelector("#oldLabel");
    const newLabel = document.querySelector("#newLabel");
    const newLabel2 = document.querySelector("#newLabel2");

    if (old === '') {
        error(oldLabel, "Fill out this field")
    }
    if (current === '') {
        error(newLabel, "Fill out this field")
    }
    if (current2 === '') {
        error(newLabel2, "Fill out this field")
    }
    if (document.querySelector('dialog').querySelector('small').style.color !== 'red') {
        form.submit();
    }


});


function error(object, message) {
    object.style.display = 'unset';
    object.textContent = message;
}
