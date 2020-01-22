Vue.component("monthly-page", {
	data: function () {
		    return {
		      resources: null,
		      role: null,
		      searchData: {}
		    }
	},
	template: ` 
		<div>
		</br>
		<h2>Monthly check</h2>
			<table>
			<tr><td>Start date:</td><td><input type="date" id="start" v-model="searchData.startDate"></td></tr>
			<tr><td>End date:</td><td><input type="date" id="end" v-model="searchData.endDate"></td>
			</table>
			<button class="btn btn-primary btn-lg" tabindex="-1" v-on:click="getData(searchData)">Done</button>
			<table class="table table-striped">
			<thead>
				<tr>
					<th>Name</th>
					<th>Price</th>
				</tr>
			</thead>
			<tbody>					
				<tr v-for="r in resources">
					<td>{{r.name }}</td>
					<td>{{r.price}}</td>
				</tr>
			</tbody>
			</table>
			
			
		</div>		  
		`
		,
		methods : {
			getData: function(searchData){
				axios
				.put('rest/monthlyCheck', this.searchData)
				.then((res) => {
					if(res.status == 200){
						
						this.resources = res.data;
						if(this.resources.length == 0){
							alert("No result");
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
					}				
				})
				.catch((res)=>{
					this.$router.push('/')
				})
			axios
			.get('rest/testAdmin')
			.then((res) => {
				if(res.status == 200){
					
				}				
			})
			.catch((res)=>{
				this.$router.push('/')
			})
	          
	        axios
	        	.get('rest/getRole')
	        	.then(res => (this.role = res.data))
	        
	    },
	});