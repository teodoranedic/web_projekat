Vue.component("vms-page", {
	data: function () {
		    return {
		      vms: null,
		      organization: {}, // ne treba trenutno dok ne smislimo kako
		      role: null,
		      searchData: {}
		    }
	},
	template: ` 
		<div>
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
					
				<tr v-for="m in vms" v-on:click="selectVM(m)">
					<td>{{m.name }}</td>
					<td>{{m.category.coreNumber}}</td>
					<td>{{m.category.RAM}}</td>
					<td>{{m.category.GPUcore}}</td>
					<td>/</td>

				</tr>
			</tbody>
			</table>
			
			<a href="#/addVMs" v-if="role!='USER'" class="btn btn-primary btn-lg" tabindex="-1" role="button"> Add New </a>
			<br>
			<table>
			<tr><td>Search by name</td><td><input type="text" id="data" v-model="searchData.name"></td></tr>
			<tr><td>Core number from:</td><td><input type="number" id="coreNumberFrom" v-model="searchData.coreNumberFrom"></td>
			<td>to:</td><td><input type="number" id="coreNumberTo" v-model="searchData.coreNumberTo"></td></tr>
			
			<tr><td>RAM from:</td><td><input type="number" id="RAMFrom" v-model="searchData.RAMFrom"></td>
			<td>to:</td><td><input type="number" id="RAMTo" v-model="searchData.RAMTo"></td></tr>
			
			<tr><td>GPU from:</td><td><input type="number" id="GPUFrom" v-model="searchData.GPUFrom"></td>
			<td>to:</td><td><input type="number" id="GPUTo" v-model="searchData.GPUTo"></td></tr>
			</table>
			<a href="" class="btn btn-primary btn-lg" tabindex="-1" role="button" v-on:click="search(searchData)">Search</a>
		

		</div>		  
		`
		,
		methods : {
			selectVM : function(v){
				this.$router.push('/vms/edit/'+v.name)
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
	          .then(res => (this.vms = res.data))
	          
	        axios
	        	.get('rest/getRole')
	        	.then(res => (this.role = res.data))
	        
	    },
	});