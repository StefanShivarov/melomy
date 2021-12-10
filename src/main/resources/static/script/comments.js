const songId = document.getElementById('songId').value;
const csrfHeaderName = document.head.querySelector('[name="_csrf_header"]').content;
const csrfHeaderValue = document.head.querySelector('[name="_csrf"]').content;
const commentsContainer = document.getElementById('commentsContainer');
const commentForm = document.getElementById('commentForm');

commentForm.addEventListener('submit', handleCommentSubmit);

const allComments = [];

const displayComments = (comments) => {
    commentsContainer.innerHTML = comments.reverse().map(
        (c)=>{
            return asComment(c)
        }
    ).join('')
    console.log(comments);
}

async function handleCommentSubmit(event){

    event.preventDefault();

    const form = event.currentTarget;
    const url = form.action;
    const formData = new FormData(form);
    console.log(formData);

    try{
        const responseData = await postFormDataAsJson({url, formData});
        commentsContainer.insertAdjacentHTML('afterbegin', asComment(responseData));
        form.reset();
    }catch (error){

        let errorObj = JSON.parse(error.message);

        if(errorObj.fieldWithErrors){
            errorObj.fieldWithErrors.forEach(
                e => {
                    let elementWithError = document.getElementById(e);
                    if(elementWithError){
                        elementWithError.classList.add('is-invalid');
                    }
                }
            )
        }
    }
}

async function postFormDataAsJson({url, formData}) {

    const plainFormData = Object.fromEntries(formData.entries());
    const formDataAsJSONString = JSON.stringify(plainFormData);

    const fetchOptions = {
        method: "POST",
        headers: {
            [csrfHeaderName] : csrfHeaderValue,
            "Content-Type" : "application/json",
            "Accept" :"application/json"
        },
        body: formDataAsJSONString
    }

    const response = await fetch(url, fetchOptions);

    if (!response.ok) {
        const errorMessage = await response.text();
        throw new Error(errorMessage);
    }

    return response.json();
}

function asComment(c) {
    let commentHtml = `<div id="comment-no-${c.id}" class="comment-field">`
    commentHtml += `<h4>${c.authorName}</h4>`
    commentHtml += `<p class="comment-date">${c.created}</p>`
    commentHtml += `<p>${c.message}</p>`
    commentHtml += `</div>`

    return commentHtml;

}

fetch(`http://localhost:8080/api/${songId}/comments`)
    .then(response => response.json())
    .then(data => {
        for(let comment of data){
            allComments.push(comment)
        }
        displayComments(allComments)
    })

