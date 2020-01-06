Vue.component("categories", {
	data: function () {
		    return {
		      categories: null
		    }
	},
	template: ` 
		<div>
		</br>
			<table class="table table-striped">
			<thead>
				<tr>
					<th>Name</th>
					<th>Number of cores</th>
					<th>RAM</th>
					<th>GPU cores</th>
				</tr>
			</thead>
			<tbody>					
				<tr v-for="c in categories" v-on:click="selectCat(c)">
					<td>{{c.name}}</td>
					<td>{{c.coreNumber}}</td>
					<td>{{c.RAM}}</td>
					<td>{{c.GPUcore}}</td>					
				</tr>
			</tbody>
			</table>
			
			<a href="#/addCategory" class="btn btn-primary btn-lg" tabindex="-1" role="button"> Add New </a>

		</div>		  
		`
		,
		methods : {
			selectCat : function(cat){
				this.$router.push('/cat/edit/'+cat.name)
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
	          .get('rest/getAllCat')
	          .then(res => (this.categories = res.data))
	          

	    },
	});