const modal = document.querySelector('dialog');
const cancelButton = document.querySelector('dialog #modal-button-cancel');
const confirmButton = document.querySelector('dialog #modal-button-confirm');
const fab = document.querySelector('.fab');
const error = document.querySelector('.error');

fab.addEventListener('click', () => {
    error.style.display = 'none';
    modal.showModal();
});

cancelButton.addEventListener('click', () => {
    modal.close();
});

confirmButton.addEventListener('click', () => {
    const input = modal.querySelector('#textInput');
    const inputValue = input.value.trim();
    if (inputValue === '') {
        error.style.display = 'unset';
        modal.querySelector('#textInput').focus();
    } else {
        error.style.display = 'none';
        modal.querySelector('form').submit();
    }
});
