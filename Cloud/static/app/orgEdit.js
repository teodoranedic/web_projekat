Vue.component("orgEdit-page", {
	data: function () {
		    return {
		      org: {}
		    }
	},
	template: ` 
		<div>
		</br>
			<table class="table table-striped">
			<tbody>
					
				<tr>
					<td>Name</td>
					<td><input type="text"  v-model="org.name"/></td>
				</tr>
				<tr>
					<td>Description</td>
					<td><input type="text" v-model = "org.description"/></td>
				</tr>
				<tr>
					<td>Logo</td>
					<td><input type="file"/></td>
				</tr>
			</tbody>
			</table>
			
			<a href="#/org" class="btn btn-primary btn-lg" tabindex="-1" role="button" v-on:click="deleteOrg()"> Delete org </a>
			<a href="#/org" class="btn btn-primary btn-lg" tabindex="-1" role="button" v-on:click="cancel()"> Cancel </a>
			<a href="#/org" class="btn btn-primary btn-lg" tabindex="-1" role="button" v-on:click="save()"> Save </a>




		</div>		  
		`
		, 
		methods : {
			deleteOrg : function() {
				axios
					.delete('rest/deleteOrg/' + this.$route.params.name, {data: this.org})
					.then(res => (this.org = res.data))
			},
	
			cancel : function(){
				this.$router.push('/org')
			},
			
			save : function(){
				
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
	          .get('rest/getOrg/' + this.$route.params.name)
	          .then(res => (this.org = res.data))
	    },
	});