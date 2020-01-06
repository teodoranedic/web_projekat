Vue.component("orgAdd-page", {
	data: function () {
		    return {
		    	orgData : {},
		    	greska : ''
		    }
	},
	template: ` 
		<div>
		</br>
			 {{greska}}
			<table class="table table-striped">
			<tbody>
					
				<tr>
					<td>Name</td>
					<td><input type="text"  v-model="orgData.name" required/></td>
				</tr>
				<tr>
					<td>Description</td>
					<td><input type="text"  v-model="orgData.description" required /></td>
				</tr>
				<tr>
					<td>Logo</td>
					<td><input type="file" v-on="orgData.imagePath" required/></td>
				</tr>
			</tbody>
			</table>
			
			<a href="#/org" class="btn btn-primary btn-lg" tabindex="-1" role="button" v-on:click="cancel()"> Cancel </a>
			<a href="#/org" class="btn btn-primary btn-lg" tabindex="-1" role="button" v-on:click="save(orgData)"> Save </a>




		</div>		  
		`
		, 
		methods : {
			
			cancel : function(){
				this.$router.push('/org')
			},
			
			save : function (orgData) {
				axios
				.post('rest/addOrg', {"name": orgData.name, "description": orgData.description, "imagePath": orgData.imagePath})
				.then((res) => {
					if(res.status == 200){
				        this.greska = '';
						this.$router.push('/org');
					}
					
					
					 // prelazi na drugi path sa push
				})
				.catch((res)=>{
					//this.$router.push('/page')
					// ne radi kako treba
					this.greska = 'Error'
				})
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
		}
	});