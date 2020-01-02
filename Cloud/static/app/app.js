//const WebShop = { template: '<web-shop></web-shop>' }
//const ShoppingCart = { template: '<shopping-cart></shopping-cart>' }
const LogIn = {template: '<log-in></log-in>'}

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    //{ path: '/', component: WebShop},
	    //{ path: '/sc', component: ShoppingCart }
		  {path: '/', component: LogIn},
		  {path: '/page', component: LogIn}
	  ]
});

var app = new Vue({
	router,
	el: '#cloud'
});

