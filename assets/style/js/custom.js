
/* GA */

  // Create Function
  function seAll() {
    $(function(){
      $('.btn-reject, .btn-add, .btn-add-profile-notfriend, .btn-accept, .btn-plus').click(function(e){
        e.preventDefault();
        var bra = $( this ).parent().parent().parent().parent();
        var bgh = bra.find( ".media-heading" );
        /*
        if ( $( this ).hasClass('btn-reject') ) {
          bra.slideUp( "slow", function() {
            $( "#msgCon" ).slideUp( "fast", function() {
              $( this ).text( $( bgh, this ).text() + " has been removed." );
            }).slideDown( "fast" );
          });
        }
        if ( $( this ).hasClass('btn-add') ) {
          bra.slideUp( "slow", function() {
            $( "#msgCon" ).slideUp( "fast", function() {
              $( this ).text( "Request to " + $( bgh, this ).text() + " has been sent." );
            }).slideDown( "fast" );
          });
        }
        if ( $( this ).hasClass('btn-accept') ) {
          bra.slideUp( "slow", function() {
            $( "#msgCon" ).text( $( bgh, this ).text() + " has been approved." );
          });
        }
        if ( $( this ).hasClass('btn-plus') ) {
          bra.slideUp( "slow", function() {
            $( "#msgCon" ).text( $( bgh, this ).text() + " has been followed." );
          });
        }
        */
        if ($(this).hasClass('btn-add-profile-notfriend')) {
          var braa = $(this).parents('.media');
          var bgha = braa.find('h4');
          var partyIdTo = braa.find('#partyIdTo').val();
          // alert(partyIdTo);        
          // console.log(partyIdTo);
          $.ajax({
            url :'form-connect-friend-request',
            type: "POST",
            data: {partyIdTo:partyIdTo}
          })
            .done(function(){
              $('.btn-add-profile-notfriend').addClass('active').attr("disabled", "disabled");
              $(bgha).text('Request Sent');
              //window.location.reload();
            })
        }
      });
    });
  }
  // Call It
  seAll();

  $(function() {

    // Javascript to enable link to tab
    var url = document.location.toString();
    if (url.match('#')) {
        $('.nav-tabs a[href=#'+url.split('#')[1]+']').tab('show');
    } 

    // Change hash for page-reload
    $('.nav-tabs a').on('shown', function (e) {
        window.location.hash = e.target.hash;
    })

    // Another add function remove some element
    $('.tab-search a').click(function() {
      $('#pagecontent').remove();
    });

  });

  // Change Theme
  $('.ct').click(function() {
    $('.photo-profile, .headerSearchContainer, .badgeClear').css('transition', 'all 0.5s ease');
    // $('.cover .cover-img').css('backgroundImage', 'url(https://localhost:8443/mobileweb/style/img/media/banner-profile-ct.jpg)');
    $('#headerwp').css({'background': '#FFF', 'background': '-moz-linear-gradient(top, #FFF 1%, #FFF 100%)', 'background': '-webkit-gradient(linear, left top, left bottom, color-stop(1%,#FFF), color-stop(100%,#FFF))', 'background': '-webkit-linear-gradient(top, #FFF 1%,#FFF 100%)', 'background': '-o-linear-gradient(top, #FFF 1%,#FFF 100%)', 'background': '-ms-linear-gradient(top, #FFF 1%,#FFF 100%)', 'background': 'linear-gradient(to bottom, #FFF 1%,#FFF 100%)', 'filter': 'progid:DXImageTransform.Microsoft.gradient( startColorstr="#FFF", endColorstr="#FFF",GradientType=0'});
    $('.headerSearchContainer').css({'border-radius': '14px', 'background' : '#333'});
    $('.badgeClear').css('background', '#555');
    $('.nav-menu ul li a').css('color', '#333');
    $('.btn-upload').remove();
    $('.photo-profile').css({'borderRadius' : '40px', 'margin' : '16px auto'});
    $('.texts-2line').prependTo('.unrow .uncol:last-child').css('textAlign', 'center');
    $('.btn-setting').prependTo('.cta').css('backgroundColor', 'rgb(221, 221, 221)');
    $('.unrow .uncol:last-child').css('padding', '16px 0');
    $('.unrow .uncol:last-child').removeClass('col-xs-6 col-sm-6 col-xs-6').addClass('col-xs-12 col-sm-12');
    $('.unrow .uncol:first-child').remove();
    $('.detail-description').css({'backgroundColor' : '#EDF0F1', 'border' : '2px solid #E4ECEA'});
    $('.tittle').css({'margin' : '5px 10px', 'textAlign' : 'left', 'fontSize' : '14px', 'color' : '#333'});
    $('.detail-description .exp').css('marginBottom', '0')
    $('.exp h4').css({'marginBottom' : '6px', 'fontSize' : '14px'});
  });

/* GA */
  
/* Plugins Prototype */

  /* Mmenu */

    // Even though this isn't used
    // This code needed to hide element on our footer
    /*
    $(function() {
      $('nav#menu-left').mmenu({
        classes   : 'mm-light'
      });
    });
    */
  /* Mmenu */

  /* Carousel */  

    $('#foto-karya').carousel({
      interval: 4000
    })

    $('.carousel .item').each(function(){
      var next = $(this).next();
      if (!next.length) {
        next = $(this).siblings(':first');
      }
      next.children(':first-child').clone().appendTo($(this));
      for (var i=0;i<1;i++) {
        next=next.next();
        if (!next.length) {
          next = $(this).siblings(':first');
        }
        next.children(':first-child').clone().appendTo($(this));
      }
    });

    $('#video-karya').carousel({
      interval: 4000
    })

  /* Carousel */

  /* Pretty Photo */

    $(document).ready(function(){
      $("a[rel^='prettyPhoto']").prettyPhoto();
    });

  /* Pretty Photo */

  /* IMG to BKG */

    $("img.convert-img").each(function(i, elem) {
      var img = $(elem);
      var div = $("<div class='cover-img' />").css({
        background: "url(" + img.attr("src") + ") no-repeat center center",
        width: "100%",
        height: "100%",
        backgroundSize: "cover",
        backgroundSize: "cover",
        webkitBackgroundSize: "cover",
        OBackgroundSize: "cover",
        MozBackgroundSize: "cover",
      });
      img.replaceWith(div);
    });

  /* IMG to BKG */

  /* Clear Function */

    function resetTextForm() {
      document.getElementById("searchForm").reset();
    }

  /* End Clear */

/* Plugins Prototype */

//bootstrap tabbing

$('#myTab a').click(function (e) {
  e.preventDefault()
  $(this).tab('show')
})

// isotope
$(window).load(function() {
  var $container = $('.example'),
    colWidth = function () {
      var w = $container.width(), 
        columnNum = 1,
        columnWidth = 0;
      if (w > 1280) {
        columnNum  = 5;
      } else if (w > 800 && w <= 1280) {
        columnNum  = 4;
      } else if (w > 480 && w <= 800) {
        columnNum  = 3;
      } else if (w <= 480) {
        columnNum  = 2;
      }
      columnWidth = Math.floor(w/columnNum);
      $container.find('.item').load().each(function() {
        var $item = $(this),
          multiplier_w = $item.attr('class').match(/item-w(\d)/),
          width = multiplier_w ? columnWidth*multiplier_w[1]-4 : columnWidth-4;
        $item.css({
          width: width,
        });
      });
      return columnWidth;
    },
    isotope = function (){
      $container.isotope({
        resizable: false,
        itemSelector: '.item',
        masonry: {
          columnWidth: colWidth(),
          gutterWidth: 4
        }
      });
    };
  isotope();
  $(window).smartresize(isotope);
});