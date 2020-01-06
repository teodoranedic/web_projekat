Vue.component("organization-page", {
	data: function () {
		    return {
		      orgs: null
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
					
				</tr>
			</tbody>
			</table>
			
			<a href="#/addOrg" class="btn btn-primary btn-lg" tabindex="-1" role="button"> Add New </a>

		</div>		  
		`
		, 
		methods : {
			selectOrg : function(org){
				this.$router.push('/org/edit/'+org.name)
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
	          .get('rest/getAllOrg')
	          .then(res => (this.orgs = res.data))
	    },
	});