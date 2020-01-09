Vue.component("users-page", {
	data: function () {
		    return {
		      users: null,
		      role: null
		    }
	},
	template: ` 
		<div>
		</br>
			<table class="table table-striped">
			<thead>
				<tr>
					<th>Email</th>
					<th>Name</th>
					<th>Last name</th>
					<th>Organization</th>
					<th>Role</th>
				</tr>
			</thead>
			<tbody>
					
				<tr v-for="u in users" v-on:click="selectUser(u)">
					<td>{{u.email}}</td>
					<td>{{u.name}}</td>
					<td>{{u.lastName}}</td>
					<td>{{u.organization.name}}</td>
					<td>{{u.role}}</td>
					
				</tr>
			</tbody>
			</table>
			
			<a href="#/addUser" class="btn btn-primary btn-lg" tabindex="-1" role="button"> Add New </a>

		</div>		  
		`
		,
		methods : {
			selectUser : function(user){
				this.$router.push('/user/edit/'+user.email)
			}
	
		},
		mounted () {
			axios
				.get('rest/testlogin')
				.then((res) => {
					if(res.status == 200){
						//this.$router.push('/');
					}				
				})
				.catch((res)=>{
					this.$router.push('/')
				})
			
	        axios
	          .get('rest/getAllUser')
	          .then(res => (this.users = res.data))
	          
	        axios
	        	.get('rest/getRole')
	        	.then(res => (this.role = res.data))
	    },
	});