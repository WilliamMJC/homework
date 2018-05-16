/* Set the defaults for DataTables initialisation */

$.extend( true, $.fn.dataTable.defaults, {

    // "sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",

    "sPaginationType": "pageredirect",

    "oLanguage": {

        "sSearch": "快速搜索:",

        "bAutoWidth": true,

        "sLengthMenu": "每页显示 _MENU_ 条记录",

        "sZeroRecords": "Nothing found - 没有记录",

        "sInfo": "_START_ 到 _END_ 条,共 _TOTAL_ 条",

        "sInfoEmpty": "显示0条记录",

        "sInfoFiltered": "(从 _MAX_ 条中过滤)",

        // "sProcessing":"<div style=''><img src='../static/img/loader.gif'><span>加载中...</span></div>",

        "oPaginate": {

            "sPrevious": "",

            "sNext":     "",

            "sLast":     ">>",

            "sFirst":    "<<"

        }

    }

} );

  

  

/* Default class modification */

$.extend( $.fn.dataTableExt.oStdClasses, {

    "sWrapper": "dataTables_wrapper form-inline"

} );

  

  

/* API method to get paging information */

$.fn.dataTableExt.oApi.fnPagingInfo = function ( oSettings )

{

    return {

        "iStart":         oSettings._iDisplayStart,

        "iEnd":           oSettings.fnDisplayEnd(),

        "iLength":        oSettings._iDisplayLength,

        "iTotal":         oSettings.fnRecordsTotal(),

        "iFilteredTotal": oSettings.fnRecordsDisplay(),

        "iPage":          Math.ceil( oSettings._iDisplayStart / oSettings._iDisplayLength ),

        "iTotalPages":    Math.ceil( oSettings.fnRecordsDisplay() / oSettings._iDisplayLength )

    };

};

  

      

  

  

/* Bootstrap style pagination control */

$.extend( $.fn.dataTableExt.oPagination, {

    "pageredirect": {

        "fnInit": function( oSettings, nPaging, fnDraw ) {

            var oLang = oSettings.oLanguage.oPaginate;

            var fnClickHandler = function ( e ) {

                e.preventDefault();

                if ( oSettings.oApi._fnPageChange(oSettings, e.data.action) ) {

                    fnDraw( oSettings );

                }

            };

  

            $(nPaging).addClass('pagination').append(

                '<ul>'+

                '<li class="first disabled"><a href="#">'+oLang.sFirst+'</a></li>'+

                '<li class="prev disabled"><a href="#">'+oLang.sPrevious+'</a></li>'+

                '<li class="next disabled"><a href="#">'+oLang.sNext+'</a></li>'+

                '<li class="last disabled"><a href="#">'+oLang.sLast+'</a></li>'+


                '到第<input type="text" id="redirect" class="redirect">页'+

               '<button class="little-nsbutton">确定</button>'+
                '</ul>'

                );

  

            //datatables分页跳转

            $(nPaging).find(".little-nsbutton").click(function(e){
                var ipage = parseInt($("#redirect").val());
                var testNumber = /^\+?[1-9]\d*$/;
                if(testNumber.test(ipage)){
                var oPaging = oSettings.oInstance.fnPagingInfo();

                var timer = null;

                if(isNaN(ipage) || ipage<1){

                    ipage = '';

                }else if(ipage>oPaging.iTotalPages){

                    ipage=oPaging.iTotalPages;

                }

                $(this).val(ipage);

                ipage--;

                oSettings._iDisplayStart = ipage * oPaging.iLength;

                 

                clearTimeout(timer);

                timer = setTimeout(function(){

                    fnDraw( oSettings );

                },600);
                }
            });

  

            var els = $('a', nPaging);

            $(els[0]).bind( 'click.DT', {

                action: "first"

            }, fnClickHandler );

            $(els[1]).bind( 'click.DT', {

                action: "previous"

            }, fnClickHandler );

            $(els[2]).bind( 'click.DT', {

                action: "next"

            }, fnClickHandler );

            $(els[3]).bind( 'click.DT', {

                action: "last"

            }, fnClickHandler );

        },

  

        "fnUpdate": function ( oSettings, fnDraw ) {

            var iListLength = 5;

            var oPaging = oSettings.oInstance.fnPagingInfo();

            var an = oSettings.aanFeatures.p;

            var i, ien, j, sClass, iStart, iEnd, iHalf=Math.floor(iListLength/2);

  

            if ( oPaging.iTotalPages < iListLength) {

                iStart = 1;

                iEnd = oPaging.iTotalPages;

            }

            else if ( oPaging.iPage <= iHalf ) {

                iStart = 1;

                iEnd = iListLength;

            } else if ( oPaging.iPage >= (oPaging.iTotalPages-iHalf) ) {

                iStart = oPaging.iTotalPages - iListLength + 1;

                iEnd = oPaging.iTotalPages;

            } else {

                iStart = oPaging.iPage - iHalf + 1;

                iEnd = iStart + iListLength - 1;

            }

  

            for ( i=0, ien=an.length ; i<ien ; i++ ) {

                // Remove the middle elements

                ($('li:gt(1)', an[i]).filter(':not(:last)')).filter(':not(:last)').remove();

  

                // Add the new list items and their event handlers

                for ( j=iStart ; j<=iEnd ; j++ ) {

                    sClass = (j==oPaging.iPage+1) ? 'class="active"' : '';

                    $('<li '+sClass+'><a href="#">'+j+'</a></li>')

                    .insertBefore( $('.next', an[i])[0] )

                    .bind('click', function (e) {

                        e.preventDefault();

                        oSettings._iDisplayStart = (parseInt($('a', this).text(),10)-1) * oPaging.iLength;

                        fnDraw( oSettings );

                    } );

                }

  

                // Add / remove disabled classes from the static elements

                if ( oPaging.iPage === 0 ) {

                    $('li:lt(2)', an[i]).addClass('disabled');

                } else {

                    $('li:lt(2)', an[i]).removeClass('disabled');

                }

  

                if ( oPaging.iPage === oPaging.iTotalPages-1 || oPaging.iTotalPages === 0 ) {

                    $('.next', an[i]).addClass('disabled');

                    $('li:last', an[i]).addClass('disabled');

                } else {

                    $('.next', an[i]).removeClass('disabled');

                    $('li:last', an[i]).removeClass('disabled');

                }

            }

        }

    }

} );

  

  

/*

 * TableTools Bootstrap compatibility

 * Required TableTools 2.1+

 */

if ( $.fn.DataTable.TableTools ) {

    // Set the classes that TableTools uses to something suitable for Bootstrap

    $.extend( true, $.fn.DataTable.TableTools.classes, {

        "container": "DTTT btn-group",

        "buttons": {

            "normal": "btn",

            "disabled": "disabled"

        },

        "collection": {

            "container": "DTTT_dropdown dropdown-menu",

            "buttons": {

                "normal": "",

                "disabled": "disabled"

            }

        },

        "print": {

            "info": "DTTT_print_info modal"

        },

        "select": {

            "row": "active"

        }

    } );

  

    // Have the collection use a bootstrap compatible dropdown

    $.extend( true, $.fn.DataTable.TableTools.DEFAULTS.oTags, {

        "collection": {

            "container": "ul",

            "button": "li",

            "liner": "a"

        }

    } );

}