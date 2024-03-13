// Create a Stripe client
var stripe = Stripe('pk_test_51MaLPBHRO81SSIrJ9mLvkYfQzLs727WkVu7xn9txkjkYk93al1mBPQJqdSJBPh6Nk33fDxd9hdsk86eObzJHAzEx00bZRtNIBa');

// Create an instance of Elements
var elements = stripe.elements();

// Create an instance of the card Element
var cardElement = elements.create('card');

// Add an instance of the card Element into the `card-element` div
cardElement.mount('#card-element');

// Handle real-time validation errors from the card Element
cardElement.addEventListener('change', function(event) {
  var displayError = document.getElementById('card-errors');
  if (event.error) {
    displayError.textContent = event.error.message;
  } else {
    displayError.textContent = '';
  }
});

// Handle form submission
var form = document.getElementById('payment-form');
form.addEventListener('submit', function(event) {
  event.preventDefault();
  
  // Disable the submit button to prevent multiple submissions
  document.getElementById('submit-button').disabled = true;

  // Create payment method with card Element and handle any errors
  stripe.createPaymentMethod({
    type: 'card',
    card: cardElement,
    billing_details: {
      name: document.getElementById('card-holder-name').value
    }
  }).then(function(result) {
    if (result.error) {
      // Show errors to the customer
      var errorElement = document.getElementById('card-errors');
      errorElement.textContent = result.error.message;
      
      // Re-enable the submit button
      document.getElementById('submit-button').disabled = false;
    } else {
      // Tokenize the payment method
      stripeTokenHandler(result.paymentMethod);
    }
  });
});

// Function to send the token to your server
function stripeTokenHandler(paymentMethod) {
  // Insert the paymentMethod.id into the form as a hidden input field
  var form = document.getElementById('payment-form');
  var hiddenInput = document.createElement('input');
  hiddenInput.setAttribute('type', 'hidden');
  hiddenInput.setAttribute('name', 'paymentMethodId');
  hiddenInput.setAttribute('value', paymentMethod.id);
  form.appendChild(hiddenInput);
  
  // Submit the form
  form.submit();
}
