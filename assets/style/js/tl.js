
// Creating AJAX function and added to JQuery
(function( $ ){
  $.fn.tlts = function() {
    $.ajax({
      url: "timeline-topstories",
      cache: false
    })
      .done(function( html ) {
        $( "#tlts" ).append( html );
      });
  };
  $.fn.tlch = function() {
    $.ajax({
      url: "timeline-connection-have",
      cache: false
    })
      .done(function( html ) {
        $( "#tlch" ).append( html );
      });
  };
  $.fn.tlcf = function() {
    $.ajax({
      url: "timeline-connection-friend",
      cache: false
    })
      .done(function( html ) {
        $( "#tlcf" ).append( html );
      });
  };
  $.fn.tlcnf = function() {
    $.ajax({
      url: "timeline-connection-notfriend",
      cache: false
    })
      .done(function( html ) {
        $( "#tlcnf" ).append( html );
      });
  };
  
  $.fn.tlsh = function() {
    // Back End Datas
    var TimelineIdx = $('#timelineId').val();
    var WhatIdx     = $('#whatId').val();

    $.ajax({
      url: "timeline-status-withheader",
      data: { TimelineId: TimelineIdx, WhatId: WhatIdx },
      cache: false,
      success: function( html ) {

        $("#tlsh").append(html);
        $(".btn-mom").click(function(e) {

          e.stopImmediatePropagation();
          e.preventDefault();

          var blp    = $(this).parent().parent().parent().parent().parent().find('.text-stat');
          var bls    = $(this).parent().parent().parent().parent().parent().find('#tl'); // tl = totalLike
          var num    = parseInt($.trim(bls.html()));
          var tlp    = bls.html(num+1);

          var idFrom = $(this).parent().find('#idFrom').val();
          var idTo   = $(this).parent().find('#idTo').val();
          var postId = $(this).parent().find('#postId').val();
          
          if ($(this).hasClass("on")) {

              $(this).removeClass("on");
              
              $.ajax({
                type: "POST",
                url: "add-like-save",
                data: { partyIdFrom : idFrom, partyIdTo : idTo, partyPostId : postId },
                success: function() {

                  if (bls.html(num === 0)) {
                    blp.empty();
                  }

                  else {
                    var tlm = bls.html(num-1);
                    console.log(tlm.text());

                    console.log("idFrom : " + idFrom);
                    console.log("idTo   : " + idTo);
                    console.log("postId : " + postId);
                  }

                }
              });
          }

          else {

            $(this).addClass("on");
            
            $.ajax({
              type: "POST",
              url: "add-like-save",
              data: { partyIdFrom : idFrom, partyIdTo : idTo, partyPostId : postId },
              success: function() {

                if (bls.length === 0) {
                  blp.append('<i class="ico-like-small"></i> <span id="tl">1</span> likes &nbsp;');
            
                  // console.log("idFrom : " + idFrom);
                  // console.log("idTo   : " + idTo);
                  // console.log("postId : " + postId);
                }

                else {
                  var tlm = bls.html(num+1);
                  console.log(tlm.text());
            
                  console.log("idFrom : " + idFrom);
                  console.log("idTo   : " + idTo);
                  console.log("postId : " + postId);
                }

              }
            });

            // kill the request
            // this abort request probably not work, cause this is used on different conditional
            // atl.abort()
          }
        });
      }
    })
  };
  $.fn.tlss = function() {
    // Back End Datas
    var TimelineIdx=$('#timelineId').val();
    var WhatIdx=$('#whatId').val();
    
    $.ajax({
      url: "timeline-status",
      data: { TimelineId: TimelineIdx, WhatId: WhatIdx },
      cache: false,
      success: function( html ) {
        $("#tlss").append(html);
        $(".btn-mom").click(function(e) {
          e.stopImmediatePropagation();
          e.preventDefault();
          var bls = $(this).parent().parent().parent().parent().find('#tl'); // tl = totalLike
          var num = parseInt($.trim(bls.html()));
          var idFrom = $(this).parent().find('#idFrom').val();
          var idTo = $(this).parent().find('#idTo').val();
          var postId = $(this).parent().find('#postId').val();
          
          if ($(this).hasClass("on")) {
              $(this).removeClass("on");
              
              $.ajax({
                  type: "POST",
                  url: "add-like-save",
                  data: {partyIdFrom:idFrom, partyIdTo:idTo, partyPostId:postId},
                  success: function() {
                   var tlm = bls.html(num-1);
                      console.log(tlm.text());
                      console.log("idFrom : "+idFrom);
                      console.log("idTo : "+idTo);
                      console.log("postId : "+postId);

                  }
                });
          }
          else {
            $(this).addClass("on");
            console.log("ini namanya timeline-status ");
                        
            console.log("idFrom : "+idFrom);
            console.log("idTo : "+idTo);
            console.log("postId : "+postId);
            
             $.ajax({
               type: "POST",
               url: "add-like-save",
               data: {partyIdFrom:idFrom, partyIdTo:idTo, partyPostId:postId},
               success: function() {
                 var tlm = bls.html(num+1);
                   console.log(tlm.text());
               }
             });

            // kill the request
            // this abort request probably not work, cause this is used on different conditional
            // atl.abort()
          }
        });
      }
    })
  };

  $.fn.tltsl = function() {
    $.ajax({
      url: "timeline-topstories-light",
      cache: false
    })
      .done(function( html ) {
        $( "#tltsl" ).append( html );
      });
  };
  $.fn.tlas = function() {
    $.ajax({
      url: "timeline-added-skill",
      cache: false
    })
      .done(function( html ) {
        $( "#tlas" ).append( html );
      });
  };
  $.fn.tlsj = function() {
    $.ajax({
      url: "timeline-suggested-job",
      cache: false
    })
      .done(function( html ) {
        $( "#tlsj" ).append( html );
      });
  };
  $.fn.tlsp = function() {
    $.ajax({
      url: "timeline-suggested-project",
      cache: false
    })
      .done(function( html ) {
        $( "#tlsp" ).append( html );
      });
  };
  $.fn.tlfc = function() {
    $.ajax({
      url: "timeline-follow-company",
      cache: false
    })
      .done(function( html ) {
        $( "#tlfc" ).append( html );
      });
  };
  $.fn.tlfs = function() {
    $.ajax({
      url: "timeline-follow-store",
      cache: false
    })
      .done(function( html ) {
        $( "#tlfs" ).append( html );
      });
  };
})( jQuery );
      
// Show it up
/*
for ( var i = 0; i < 12; i++ ) {
  if (i < 1) {
    // Top Stories
    $('html').tlts();
  }
  else if (i < 2) {
    // Connection Have
    $('html').tlch();
  }
  else if (i < 3) {
    // Connection Have
    $('html').tlcf();
  }
  else if (i < 4) {
    // Connection Not Friend
    $('html').tlcnf();
  }
  else if (i < 5) {
    // Status Header
    $('html').tlsh();
  }
  else if (i < 6) {
    // Status
    $('html').tlss();
  }
  else if (i < 7) {
    // Top Stories Light
    $('html').tltsl();
  }
  else if (i < 8) {
    // Added Skill
    $('html').tlas();
  }
  else if (i < 9) {
    // Suggested Job
    $('html').tlsj();
  }
  else if (i < 10) {
    // Suggested Project
    $('html').tlsp();
  }
  else if (i < 11) {
    // Follow Company
    $('html').tlfc();
  }
  else {
    // Follow Store
    $('html').tlfs();
  }
}
*/