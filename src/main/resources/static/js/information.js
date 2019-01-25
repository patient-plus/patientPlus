//Side project to make UI better

$(document).ready(function () {
    //@naresh action dynamic childs
    // let next = 0;
    // let operations = "${surgeries[" + next + "].operation}";
    // let date = "${surgeries[" + next + "].date}";
    // $("#add-more").click(function(){
    //     let addto = "#field" + next;
    //     let addRemove = "#field" + (next);
    //     next = next + 1;
    //     operations = "${surgeries[" + next + "].operation}";
    //     date = "${surgeries[" + next + "].date}";
    //     let newIn = `<div id="field`+ next +`" name="field`+ next +`"><ul class="list-group list-group-flush">
    //                     <li class="list-group-item">
    //                         <label for="operation` + next + `">Operation</label>
    //                         <input type="text" class="form-control" id="operation` + next + `" placeholder="Operation" th:field="${operations}"/>
    //                     </li>
    //                     <li class="list-group-item">
    //                         <label for="operationDate` + next + `" class="form-text text-muted ml-1 mb-0">Date of Operation:</label>
    //                         <input type="date" id="operationDate` + next + `" name="operationDate` + next + `"
    //                                class="form-control" th:field="${date}"/>
    //                     </li>
    //                 </ul>`;
    //     let newInput = $(newIn);
    //     let removeBtn = '<button id="remove' + (next - 1) + '" class="btn btn-danger remove-me" >Remove</button></div></div><div id="field">';
    //     let removeButton = $(removeBtn);
    //     $(addto).after(newInput);
    //     $(addRemove).after(removeButton);
    //     $("#field" + next).attr('data-source',$(addto).attr('data-source'));
    //     $("#count").val(next);
    //
    //     $('.remove-me').click(function(e){
    //         e.preventDefault();
    //         let fieldNum = this.id.charAt(this.id.length-1);
    //         let fieldID = "#field" + fieldNum;
    //         $(this).remove();
    //         $(fieldID).remove();
    //     });
    // });
    //
    // let iterator = 0;
    // $("#add-more-medication").click(function(){
    //     let addto = "#form" + iterator;
    //     let addRemove = "#form" + (iterator);
    //     iterator = iterator + 1;
    //     let newIn = `<div id="form`+ iterator +`" name="form`+ iterator +`"><ul class="list-group list-group-flush">
    //                     <li class="list-group-item">
    //                         <label for="medicationName">Medication Name</label>
    //                         <input type="text" class="form-control" id="medicationName" placeholder="Medication Name" />
    //                     </li>
    //                     <li class="list-group-item">
    //                         <label for="medicationDose">Medication Dose</label>
    //                         <input type="number" class="form-control" id="medicationDose" placeholder="Medication Dose"/>
    //                     </li>
    //                 </ul>`;
    //     let newInput = $(newIn);
    //     let removeBtn = '<button id="removeMedication' + (iterator - 1) + '" class="btn btn-danger remove-medication" >Remove</button></div></div><div id="form">';
    //     let removeButton = $(removeBtn);
    //     $(addto).after(newInput);
    //     $(addRemove).after(removeButton);
    //     $("#form" + iterator).attr('data-source',$(addto).attr('data-source'));
    //     $("#count").val(iterator);
    //
    //     $('.remove-medication').click(function(e){
    //         e.preventDefault();
    //         let formNum = this.id.charAt(this.id.length-1);
    //         let formID = "#form" + formNum;
    //         $(this).remove();
    //         $(formID).remove();
    //     });
    // });

    $('.btnNext').click(function(){
        $('.nav-tabs > .active').next('a').trigger('click');
    });

    $('.btnPrevious').click(function(){
        $('.nav-tabs > .active').prev('a').trigger('click');
    });
});
