
const LogIn = {template: '<log-in></log-in>'}
const HomePage = {template: '<home-page></home-page>'}
const Organizations = {template: '<organization-page></organization-page>'}
const OrgEdit = {template: '<orgEdit-page></orgEdit-page>'}
const OrgAdd = {template: '<orgAdd-page></orgAdd-page>'}

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
		  {path: '/', component: LogIn},
		  {path: '/page', component: HomePage},
		  {path: '/org', component: Organizations},
		  {path: '/org/edit/:name', component: OrgEdit},
		  {path: '/addOrg', component: OrgAdd}		  
	  ]
});

var app = new Vue({
	router,
	el: '#cloud'
});

