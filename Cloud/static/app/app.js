
const LogIn = {template: '<log-in></log-in>'}
const HomePage = {template: '<home-page></home-page>'}
const Organizations = {template: '<organization-page></organization-page>'}
const OrgEdit = {template: '<orgEdit-page></orgEdit-page>'}
const OrgAdd = {template: '<orgAdd-page></orgAdd-page>'}
const Account = {template: '<account></account>'}
const Categories = {template: '<categories></categories>'}
const CatEdit = {template: '<catEdit-page></catEdit-page>'}
const CatAdd = {template: '<catAdd-page></catAdd-page>'}
const Users = {template: '<users-page></users-page>'}
const UserEdit = {template: '<userEdit-page></userEdit-page>'}
const UserAdd = {template: '<userAdd-page></userAdd-page>'}

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
		  {path: '/', component: LogIn},
		  {path: '/page', component: HomePage},
		  {path: '/org', component: Organizations},
		  {path: '/org/edit/:name', component: OrgEdit},
		  {path: '/addOrg', component: OrgAdd},
		  {path: '/account', component: Account},
		  {path: '/cat', component: Categories},
		  {path: '/cat/edit/:name', component: CatEdit},
		  {path: '/addCategory', component: CatAdd},
		  {path: '/users', component: Users},
		  {path: '/user/edit/:email', component: UserEdit},
		  {path: '/addUser', component: UserAdd}
	  ]
});

var app = new Vue({
	router,
	el: '#cloud'
});

