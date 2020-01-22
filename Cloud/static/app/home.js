Vue.component("home-page", {
	data: function () {
		    return {
		      vm: null,
		      role: null
		    }
	},
	template: ` 
		<div>
		<div class="container">
			<p>
				<a href="#/" class="btn btn-primary btn-lg" tabindex="-1" role="button" v-on:click="logout()"> Logout </a>
			</p>
			<p>
				<a href="#/account" class="btn btn-primary btn-lg" tabindex="-1" role="button" > Account</a>
			</p>
			<p>
				<a href="#/org" v-if="role!='USER'" class="btn btn-primary btn-lg" tabindex="-1" role="button"> Organizations</a>
			</p>
			<p>
				<a href="#/users"   v-if="role != 'USER'" class="btn btn-primary btn-lg" tabindex="-1" role="button"> Users</a>
			</p>
			<p>
				<a href="#/vms" class="btn btn-primary btn-lg" tabindex="-1" role="button"> Virtual machines</a>
			</p>
			<p>
				<a href="#/discs" class="btn btn-primary btn-lg" tabindex="-1" role="button" > Discs</a>
			</p>
			<p>
				<a href="#/cat" v-if="role=='SUPERADMIN'" class="btn btn-primary btn-lg" tabindex="-1" role="button"> Categories</a>
			</p>
			<p>
				<a href="#/monthlyCheck" v-if="role=='ADMIN'" class="btn btn-primary btn-lg disabled" tabindex="-1" role="button" aria-disabled="true"> Monthly check</a>
			</p>
			</div>
		</br>
			<table class="table table-striped">
			<thead>
				<tr>
					<th>Name</th>
					<th>Core number</th>
					<th>RAM</th>
					<th>GPU</th>
					<th>Organization</th>
				</tr>
			</thead>
			<tbody>
					
				<tr v-for="m in vm">
					<td>{{m.name }}</td>
					<td>{{m.category.coreNumber}}</td>
					<td>{{m.category.RAM}}</td>
					<td>{{m.category.GPUcore}}</td>
					<td>/</td>

				</tr>
			</tbody>
			</table>

		</div>		  
		`
		, 
		methods : {
			logout : function () {
				axios
				.get('rest/logout')
				.then((res) => res.data)
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
	          .get('rest/getAllVM')
	          .then(res => (this.vm = res.data))
	          
	        axios
	        	.get('rest/getRole')
	        	.then(res => (this.role = res.data))
	        
	        
	    },
	});