// Focus & Set Version
$('.console-input').focus();
var ver = "3.5";
$('#ver').html(ver);

// Force Lowercase Input
$('.console-input').keyup(function() {
  //this.value = this.value.toLowerCase();
});

// Force Cursor to End
$('.console-input').keydown(function() {
  this.value = this.value;
});
$('.console-input').click(function() {
  this.value = this.value;
});



// Particles BG
particlesJS('particles-js', {
  'particles': {
    'number': {
      'value': 50
    },
    'color': {
      'value': '#ffffff'
    },
    'shape': {
      'type': 'triangle',
      'polygon': {
        'nb_sides': 5
      }
    },
    'opacity': {
      'value': 0.06,
      'random': false
    },
    'size': {
      'value': 11,
      'random': true
    },
    'line_linked': {
      'enable': true,
      'distance': 150,
      'color': '#ffffff',
      'opacity': 0.4,
      'width': 1
    },
    'move': {
      'enable': true,
      'speed': 4,
      'direction': 'none',
      'random': false,
      'straight': false,
      'out_mode': 'out',
      'bounce': false
    }
  },
  'interactivity': {
    'detect_on': 'canvas',
    'events': {
      'onhover': {
        'enable': false
      },
      'onclick': {
        'enable': true,
        'mode': 'push'
      },
      'resize': true
    },
    'modes': {
      'push': {
        'particles_nb': 4
      }
    }
  },
  'retina_detect': true
}, function() {

});