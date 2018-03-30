var editor = window.pell.init({
    element: document.getElementById('pell'),
    defaultParagraphSeparator: 'p',
    styleWithCSS: true,
    onChange: function (html) {

    },
    actions: [
        'bold',
        'italic',
        'underline',
        'paragraph',
        'code',
        'line',
        'link',
        'image'
    ]
})