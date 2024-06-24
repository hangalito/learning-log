document.addEventListener('DOMContentLoaded', () => {
    if (URL.endsWith('?error')) {
        modal.showModal();
    }
});

const modal = document.querySelector('dialog');
const cancelButton = document.querySelector('dialog #modal-button-cancel');
const fab = document.querySelector('#fab');


cancelButton.addEventListener('click', () => {
    modal.close();
});

fab.addEventListener('click', () => {
    modal.showModal();
});
