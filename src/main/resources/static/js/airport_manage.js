$(document).ready(function () {
    $(".button_delete").on("click", function (e) {
        var r = confirm("Are you sure about deleting the airport");
        if (r === true) {
        } else {
            e.preventDefault();
        }
    })
})
