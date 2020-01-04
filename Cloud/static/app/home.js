Vue.component("home-page", {
	data: function () {
		    return {
		      vm: null
		    }
	},
	template: ` 
		<div>
			<p>
				<a href="#/" v-on:click="logout()"> Logout </a>
			</p>
			<p>
				<a href="#/account"> Account</a>
			</p>
			<p>
				<a href="#/org"> Organizations</a>
			</p>
			<p>
				<a href="#/users"> Users</a>
			</p>
			<p>
				<a href="#/vms"> Virtual machines</a>
			</p>
			<p>
				<a href="#/discs"> Discs</a>
			</p>
			<p>
				<a href="#/cat"> Categories</a>
			</p>
			
			Virtual machines:
			<table border="1">
				<tr bgcolor="lightgrey">
					<th>Name</th>
					<th>Core number</th>
					<th>RAM</th>
					<th>GPU</th>
					<th>Organization</th>
				</tr>
					
				<tr v-for="m in vm">
					<td>{{m.name }}</td>
					<td>{{m.category.coreNumber}}</td>
					<td>{{m.category.RAM}}</td>
					<td>{{m.category.GPUcore}}</td>

				</tr>
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
	    },
	});