Vue.component("disc-page", {
	data: function () {
		    return {
		      discs: null,
		      role: null,
		      searchData: {name: "",
		    	  			from: 0,
		    	  			to: 0}
		    }
	},
	template: ` 
		<div>
		</br>
			<table class="table table-striped">
			<thead>
				<tr>
					<th>Name</th>
					<th>Capacity</th>
					<th>VM</th>
				</tr>
			</thead>
			<tbody>
					
				<tr v-for="d in discs" v-on:click="selectDisc(d)">
					<td>{{d.name}}</td>
					<td>{{d.capacity}}</td>
					<td>{{d.virtualMachine.name}}</td>
				</tr>
			</tbody>
			</table>
			
			<a href="#/addDisc" v-if="role!='USER'" class="btn btn-primary btn-lg" tabindex="-1" role="button"> Add New </a>
			<br>
			<table>
			<tr><td>Search by name</td><td><input type="text" id="data" v-model="searchData.name"></td>
			<td>Capacity from:</td><td><input type="number" id="capacityFrom" v-model="searchData.from"></td>
			<td>to:</td><td><input type="number" id="capacityTo" v-model="searchData.to"></td></tr>
			</table>
			<button class="btn btn-primary btn-lg" tabindex="-1" v-on:click="search(searchData)">Search</button>
			<a href="#/page" class="btn btn-primary btn-lg" tabindex="-1" role="button">Home</a>
		

		</div>		  
		`
		,
		methods : {
			selectDisc : function(d){
				this.$router.push('/disc/edit/'+d.name)
			},
			
			search: function(searchData){
				axios
				.put('rest/searchDiscs', this.searchData)
				.then((res) => {
					if(res.status == 200){
						
						this.discs = res.data;
						if(this.discs.length == 0){
							alert("No result")
						}
					}
				})
				.catch((res)=>{
					alert("No results")
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
			
	        axios
	          .get('rest/getAllDiscs')
	          .then(res => (this.discs = res.data))
	          
	        axios
	        	.get('rest/getRole')
	        	.then(res => (this.role = res.data))
	    },
	});