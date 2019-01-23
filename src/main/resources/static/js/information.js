$(document).ready(function () {
    //@naresh action dynamic childs
    let next = 0;
    $("#add-more").click(function(e){
        e.preventDefault();
        let addto = "#field" + next;
        let addRemove = "#field" + (next);
        next = next + 1;
        let newIn = `<div id="field`+ next +`" name="field`+ next +`"><ul class="list-group list-group-flush">
                        <li class="list-group-item">
                            <label for="operation">Operation</label>
                            <input type="text" class="form-control" id="operation" placeholder="Operation"/>
                        </li>
                        <li class="list-group-item">
                            <label for="operationDate" class="form-text text-muted ml-1 mb-0">Date of Operation:</label>
                            <input type="date" id="operationDate" name="operationDate" max="2999-12-31" min="1920-12-31"
                                   required="required" class="form-control" />
                        </li>
                    </ul>`;
        let newInput = $(newIn);
        let removeBtn = '<button id="remove' + (next - 1) + '" class="btn btn-danger remove-me" >Remove</button></div></div><div id="field">';
        let removeButton = $(removeBtn);
        $(addto).after(newInput);
        $(addRemove).after(removeButton);
        $("#field" + next).attr('data-source',$(addto).attr('data-source'));
        $("#count").val(next);

        $('.remove-me').click(function(e){
            e.preventDefault();
            let fieldNum = this.id.charAt(this.id.length-1);
            let fieldID = "#field" + fieldNum;
            $(this).remove();
            $(fieldID).remove();
        });
    });

});