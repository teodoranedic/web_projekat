Vue.component("organization-page", {
	data: function () {
		    return {
		      orgs: null,
		      role: null
		    }
	},
	template: ` 
		<div>
		</br>
			<table class="table table-striped">
			<thead>
				<tr>
					<th>Name</th>
					<th>Description</th>
					<th>Logo</th>
				</tr>
			</thead>
			<tbody>
					
				<tr v-for="o in orgs" v-on:click="selectOrg(o)">
					<td>{{o.name}}</td>
					<td>{{o.description}}</td>
					<td><img v-bind:src="o.imagePath" height = 25 width=50></td>
					
				</tr>
			</tbody>
			</table>
			
			<a href="#/addOrg" v-if="role=='SUPERADMIN'" class="btn btn-primary btn-lg" tabindex="-1" role="button"> Add New </a>

		</div>		  
		`
		,
		methods : {
			selectOrg : function(org){
				this.$router.push('/org/edit/'+org.name)
			},
	//,
	//		imgUrl : function(path){
	//			return images('././data/' + path)
	//		}
			
	
		},
		mounted () {
			axios
				.get('rest/testlogin')
				.then((res) => {
					if(res.status == 200){
						
					}				
				})
				.catch((res)=>{
					this.$router.push('/')
				})
			
			axios
			.get('rest/testSuperadminAdmin')
			.then((res) => {
				if(res.status == 200){
					
				}				
			})
			.catch((res)=>{
				this.$router.push('/')
			})
			
			axios
	          .get('rest/getAllOrg')
	          .then(res => (this.orgs = res.data))
	          
	        axios
	        	.get('rest/getRole')
	        	.then(res => (this.role = res.data))
	        
	    },
	});