/* ! jQuery base Extend | (c) 2013, 2014 WP's */

/* Started */
  
  /* Button Actions */

    // Named Function Expression
    var serchAllBtn = function serchAllBtn() {

      $(function() {

        // Declare Each Variables
        var $actButtons = $('button[class^="btn-"]'),
            $setParent  = $(this).parents('.media'),
            $setElement = $setParent.find('h4');

        // Add Click Function
        $actButtons.click(function(e) {

          // Start Customize
          if ($(this).hasClass($addFriend)) {

            // Declare Buttons Variables
            var $addFriend = $('.btn-add-profile-notfriend'),
                // Need Change The Existing Id to Class
                $partyIdTo = $setParent.find('.partyIdTo').val();

            // Prevent Acts
            e.preventDefault();

            $.ajax({
              url  : 'form-connect-friend-request',
              type : 'POST',
              data : { partyIdTo : $partyIdTo }
            })
              .done(function() {
                $addFriend.addClass('active').attr('disabled', 'disabled');
                $setElement.text('Request Sent');
              })
          }

        });

      });

      return true;
    }

  /* Search -  Especially Welcome */

  /* Create New Function */
  
/* Finsih */