Vue.component("discEdit-page", {
	data: function () {
		    return {
		      disc: {},
		      vms: null,
		      vm: {},
		      greska: '',
		      nameErr: '',
		      typeErr: '',
		      capacityErr: ''
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
					<td><input type="text"  v-model="disc.name"/></td>
					<td style="color: red">{{nameErr}}</td>
				</tr>
				<tr>
					<td>Capacity</td>
					<td><input type="text" v-model = "disc.capacity"/></td>
					<td style="color: red">{{capacityErr}}</td>
				</tr>
				<tr>
					<td>Type</td>
					<td><select id="selectType" v-model="disc.type">
						<option value="SSD">SSD</option>
						<option value="HDD">HDD</option>
						</select>
					</td>
					<td></td>
				<tr>
					<td>Virtual machine</td>
					<td><select id="selectVM" v-model="vm.name">
						<option v-for="v in vms" :value="v.name">{{v.name}}</option>
						</select>
					</td>
					<td></td>
				</tr>
				
			</tbody>
			</table>
			
			<a href="#/discs" class="btn btn-primary btn-lg" tabindex="-1" role="button" v-on:click="deleteDisc()"> Delete disc </a>
			<a href="#/discs" class="btn btn-primary btn-lg" tabindex="-1" role="button" v-on:click="cancel()"> Cancel </a>
			<button class="btn btn-primary btn-lg" tabindex="-1"  v-on:click="save()">Save</button> 

		</div>		  
		`
		, 
		methods : {
			deleteDisc : function() {
				axios
					.delete('rest/deleteDisc/' + this.$route.params.name, {data: this.disc})
					.then((res) => {
						if(res.status == 200){
					        this.greska = '';
							this.$router.push('/discs');
						}
					})
			},
	
			cancel : function(){
				this.$router.push('/discs')
			},
			
			save : function(){
				if(this.disc.name=='')
					this.nameErr = 'Name cannot be blank.';
				if(!this.disc.capacity)
					this.capacity = 'Capacity cannot be blank';
				if(this.disc.name && this.disc.capacity){
					axios
					.put('rest/editDisc/'+this.$route.params.name, {"name": this.disc.name, "type": this.disc.type, "capacity": this.disc.capacity, "virtualMachine": this.vm })
					.then((res) => {
						if(res.status == 200){
					        this.greska = '';
							this.$router.push('/discs');
						}
					})
					.catch((res)=>{
						this.greska = 'Error'
					})
					
					
				}
				
				
			},
			
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
	          .get('rest/getDisc/' + this.$route.params.name)
	          .then(res => {this.disc = res.data;
	          				this.vm = res.data.virtualMachine;
	          })
	         axios
	         	.get('rest/getAllVM')
	         	.then(res => (this.vms = res.data))
	    },
	});