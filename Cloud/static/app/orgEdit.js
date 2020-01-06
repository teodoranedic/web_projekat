Vue.component("orgEdit-page", {
	data: function () {
		    return {
		      org: {},
		      greska: '',
		      nameErr: '',
		      descErr: '',
		      imageErr: ''
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
					<td><input type="text"  v-model="org.name"/></td>
					<td style="color: red">{{nameErr}}</td>
				</tr>
				<tr>
					<td>Description</td>
					<td><input type="text" v-model = "org.description"/></td>
					<td style="color: red">{{descErr}}</td>
				</tr>
				<tr>
					<td>Logo</td>
					<td><input type="file" @change = "onUpload"/></td>
					<td style="color: red">{{imageErr}}</td>
				</tr>
			</tbody>
			</table>
			
			<a href="#/org" class="btn btn-primary btn-lg" tabindex="-1" role="button" v-on:click="deleteOrg()"> Delete org </a>
			<a href="#/org" class="btn btn-primary btn-lg" tabindex="-1" role="button" v-on:click="cancel()"> Cancel </a>
			<button class="btn btn-primary btn-lg" tabindex="-1"  v-on:click="save()">Save</button> 

		</div>		  
		`
		, 
		methods : {
			deleteOrg : function() {
				axios
					.delete('rest/deleteOrg/' + this.$route.params.name, {data: this.org})
					.then((res) => {
						if(res.status == 200){
					        this.greska = '';
							this.$router.push('/org');
						}
					})
			},
	
			cancel : function(){
				this.$router.push('/org')
			},
			
			save : function(){
				if(this.org.name=='')
					this.nameErr = 'Name cannot be blank.';
				if(!this.org.description)
					this.descErr = 'Description cannot be blank.';
				if(!this.org.imagePath)
					this.imageErr = 'Choose a file for logo.';
				if(this.org.name && this.org.description && this.org.imagePath){
					axios
					.put('rest/editOrg/'+this.$route.params.name, this.org)
					.then((res) => {
						if(res.status == 200){
					        this.greska = '';
							this.$router.push('/org');
						}
					})
					.catch((res)=>{
						this.greska = 'Error'
					})
					
					
				}
				
				
			},
			onUpload(event) {
				this.org.imagePath = (event.target.files)[0].name;
			}
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
	          .get('rest/getOrg/' + this.$route.params.name)
	          .then(res => (this.org = res.data))
	    },
	});