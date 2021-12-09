const songId = document.getElementById('songId').value;

const commentsContainer = document.getElementById('commentsContainer');

const allComments = [];

const displayComments = (comments) => {
    commentsContainer.innerHTML = comments.map(
        (c)=>{
            return asComment(c)
        }
    ).join('')
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