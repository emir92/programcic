angular.module('hello', [ 'ngRoute' ]).config(function($routeProvider) {

	$routeProvider.when('/', {
		templateUrl : 'views/home.html',
		controller : 'home',
		controllerAs: 'controller'
	}).when('/login', {
		templateUrl : 'views/login.html',
		controller : 'navigation',
		controllerAs: 'controller'
	}).when('/grad',{
                templateUrl : 'views/grad.html',
		controller : 'kontroler',
		controllerAs: 'controller'
        }).when('/grad/list',{
                templateUrl : 'views/grad_lista.html',
		controller : 'kontroler',
		controllerAs: 'controller'
        }).when('/klub',{
                templateUrl : 'views/klub.html',
		controller : 'kontroler',
		controllerAs: 'controller'
        }).when('/klub/list',{
                templateUrl : 'views/klub_lista.html',
		controller : 'kontroler',
		controllerAs: 'controller'
        }).when('/liga',{
                templateUrl : 'views/liga.html',
		controller : 'kontroler',
		controllerAs: 'controller'
        }).when('/liga/list',{
                templateUrl : 'views/liga_ispis.html',
		controller : 'kontroler',
		controllerAs: 'controller'
        }).when('/sezona',{
                templateUrl : 'views/sezona.html',
		controller : 'kontroler',
		controllerAs: 'controller'
        }).when('/sezona/list',{
                templateUrl : 'views/sezona_edit.html',
		controller : 'kontroler',
		controllerAs: 'controller'
        }).otherwise('/');

}).controller('navigation',

function($rootScope, $http, $location, $route) {
	
	var self = this;

	self.tab = function(route) {
		return $route.current && route === $route.current.controller;
	};

	var authenticate = function(callback) {

		$http.get('user').then(function(response) {
			if (response.data.name) {
				$rootScope.authenticated = true;
			} else {
				$rootScope.authenticated = false;
			}
			callback && callback();
		}, function() {
			$rootScope.authenticated = false;
			callback && callback();
		});

	}

	authenticate();

	self.credentials = {};
	self.login = function() {
		$http.post('login', $.param(self.credentials), {
			headers : {
				"content-type" : "application/x-www-form-urlencoded"
			}
		}).then(function() {
			authenticate(function() {
				if ($rootScope.authenticated) {
					console.log("Login succeeded")
					$location.path("/");
					self.error = false;
					$rootScope.authenticated = true;
				} else {
					console.log("Login failed with redirect")
					$location.path("/login");
					self.error = true;
					$rootScope.authenticated = false;
				}
			});
		}, function() {
			console.log("Login failed")
			$location.path("/login");
			self.error = true;
			$rootScope.authenticated = false;
		})
	};

	self.logout = function() {
		$http.post('logout', {}).finally(function() {
			$rootScope.authenticated = false;
			$location.path("#/");
                        
		});
	}

}).controller('home', function($http) {
	var self = this;
	
}).controller('kontroler', ['$scope', 'Servis', function($scope, Servis) {
        var s= $scope;
        
        s.grad={idGrada:null,imeGrada:'',opcina:'',kanton:'',entitet:''};
        s.gradovi=[];
        
        s.klub={idKluba:null,imeKluba:'',idGrad:null,imeStadiona:'',imeNavijaca:''};
        s.klubovi=[];
        
        s.liga={idLige:null,imeLige:'',sklubovi:[]};
        s.lige=[];
        
        s.sezona={idSezone:null,imeSezone:'',idLige:null,klubovi:[]};
        s.sezone=[];
        
        s.list=[];
        s.izabraniKlubovi=[];
        s.lista2=[];
        s.izabranSezona=[];
        s.DajSveGradove= function(){
          Servis.DajSveGradove()
                  .then(
                           function(d) {
                                    s.gradovi = d;
                                    for(var i=0; i<s.gradovi.length;i++)
                                    {
                                        if(s.gradovi[i].opcina=="AAA") s.gradovi[i].opcina='';
                                        if(s.gradovi[i].kanton=="AAA") s.gradovi[i].kanton='';
                                    }
                           },
                            function(errResponse){
                                    console.error('Greska pri dobivanju gradova!');
                            }
                       );  
        };
        
        s.DajSveKlubove= function(){
          Servis.DajSveKlubove()
                  .then(
                           function(d) {
                                    s.klubovi = d;
                           },
                            function(errResponse){
                                    console.error('Greska pri dobivanju klubova!');
                            }
                       );  
        };
        
        s.DajSveLige= function(){
          Servis.DajSveLige()
                  .then(
                           function(d) {
                                    s.lige = d;
                           },
                            function(errResponse){
                                    console.error('Greska pri dobivanju Liga!');
                            }
                       );  
        };
        
        s.DajSveSezone= function(){
          Servis.DajSveSezone()
                  .then(
                           function(d) {
                                    s.sezone = d;
                           },
                            function(errResponse){
                                    console.error('Greska pri dobivanju Sezona!');
                            }
                       );  
        };
        
        s.CreateGrad=function(grad){
            Servis.createGrad(grad)
                .then(
                             s.DajSveGradove(), 
                                      function(errResponse){
                                               console.error('Greska pri pravljenu grada');
                                      }	
                );
        };
        
        s.CreateKlub=function(klub){
            Servis.createKlub(klub)
                .then(
                             s.DajSveKlubove(), 
                                      function(errResponse){
                                               console.error('Greska pri pravljenu kluba');
                                      }	
                );
        };
        
        s.CreateLiga=function(liga){
            Servis.createLiga(liga)
                .then(
                             s.DajSveLige(), 
                                      function(errResponse){
                                               console.error('Greska pri pravljenu lige');
                                      }	
                );
        };
        
        s.CreateSezona=function(sezona){
            Servis.createSezona(sezona)
                .then(
                             s.DajSveSezone(), 
                                      function(errResponse){
                                               console.error('Greska pri pravljenu sezone');
                                      }	
                );
        };
        
        s.updateLiga = function(liga, id){
              Servis.updateLiga(liga, id)
		              .then(
				              s.DajSveLige(), 
				              function(errResponse){
					               console.error('Error while updating User.');
				              }	
                  );
          };
        
        s.updateSezona= function(sezona,id){
            Servis.updateSezona(sezona,id)
                    .then(
                        s.DajSveSezone(),
                function(errResponse){
                    console.error("Greska  pri updejtu sezone!");
                }
                );
        };
        
        
        s.DajSveGradove();
        s.DajSveKlubove();
        s.DajSveLige();
        s.DajSveSezone();
        
        s.dodajGrad= function(){
            console.log('Dodaj novi grad', s.grad);
            s.CreateGrad(s.grad);
            s.resetGrad();
        };
        
        s.dodajKlub=function(){
            console.log('Dodaj novi klub',s.klub);
            s.klub.idGrad=s.idGrada.valueOf();
            s.CreateKlub(s.klub);
            s.resetGrad();
        };
        
        s.KrerirajLigu=function(){
            console.log('Dodaj novu ligu:',s.liga);
            s.CreateLiga(s.liga);
            s.resetGrad();
        };
        
        s.KreirajSezonu=function(){
            var indeks= s.idLiga-1+1; 
            var ind;
            for(var i=0; i<s.lige.length;i++)
            {
                if(indeks==s.lige[i].idLige) 
                {
                    s.sezona.idLige=s.lige[i].idLige;
                    ind=i;
                }
            }
            for(var i=0; i<s.izabraniKlubovi.length;i++)
            {
                s.sezona.klubovi.push(s.izabraniKlubovi[i].idKluba);
            }
            s.liga.idLige=s.lige[ind].idLige;
            s.liga.imeLige=s.lige[ind].imeLige;
            s.liga.sklubovi=s.sezona.klubovi;
            console.log(s.sezona);
            console.log(s.liga);
            s.CreateSezona(s.sezona);  
            s.DajSveSezone();
           
            s.resetGrad();
            
        }
        
        s.updateL=function(duzina)
        {
            var idL= s.sezone[duzina].idLige;
            var klub= s.sezone[duzina].klubovi;
            s.lige[idL-1].sklubovi=klub;
            s.updateLiga(s.lige[idL-1],idL)
        }
        
        s.prebacidesno=function(){
            for(var i=0; i<s.list.length;i++){
                var indeks= s.list[i]-1;   
                s.izabraniKlubovi.push(s.klubovi[indeks]);
            }
        };
        
        s.izbrisi=function (){
            for(var i=0; i<s.lista2.length;i++){
                var indeks= s.lista2[i]-1+1;
                var brojac=0;
                for(var j=0; j<s.izabraniKlubovi.length;j++){
                  if(s.izabraniKlubovi[j].idKluba==indeks) s.izabraniKlubovi.splice(j,1);
                    
                }
                
                
            }
        };
        
        s.ispisi=function(idSe){
            for(var i=0; i<s.sezone.length; i++){
                if(idSe==s.sezone[i].idSezone) var ind=i;
            }
            s.updateL(ind);
            var indeks= s.izborSezone-1+1;
            for(var i=0; i<s.sezone.length;i++)
            {
                if(indeks==s.sezone[i].idSezone) s.izabranSezona=s.sezone[i].klubovi;
            }
            
        }
        
        s.spasi=function(){
            console.log(s.izabranSezona);
            var indeks= s.izborSezone-1+1;
            for(var i=0; i<s.sezone.length;i++)
            {
                if(indeks==s.sezone[i].idSezone)
                {
                   s.sezona.idLige=s.sezone[i].idLige;
                   s.sezona.idSezone=s.sezone[i].idSezone;
                   s.sezona.imeSezone=s.sezone[i].imeSezone; 
                   s.sezona.klubovi=s.izabranSezona;
                   console.log(s.sezone[i]);
                   s.updateSezona(s.sezona,s.sezona.idSezone);
                   
                   var idL= s.sezone[i].idLige;
                   var klub= s.izabranSezona
                   s.lige[idL-1].sklubovi=klub;
                   s.updateLiga(s.lige[idL-1],idL)
                   break;
                }
            } 
            
        };
        
        s.izabranLiga=[];
        s.ispisiLigu=function(ind){
            for(var i=0; i<s.lige.length;i++){
                if(ind==s.lige[i].idLige) s.izabranLiga=s.lige[i].sklubovi;
            }
            console.log(s.izabranLiga);
        }
        
        s.resetGrad=function(){
            s.grad={idGrada:null,imeGrada:'',opcina:'',kanton:'',entitet:''};
            s.klub={idKluba:null,imeKluba:'',idGrad:'',imeStadiona:'',imeNavijaca:''};
            s.liga={idLige:null,imeLige:'',sklubovi:[]};
            s.sezona={idSezone:null,imeSezone:'',idLige:'',klubovi:[]};
        };
}]).factory('Servis', ['$http', '$q', function($http, $q){
        return {
          DajSveGradove: function() {
                return $http.get('http://localhost:8080/gradovi')
                                .then(
                                                function(response){
                                                        return response.data;
                                                }, 
                                                function(errResponse){
                                                        console.error('Greska pri dobivanju gradova');
                                                        return $q.reject(errResponse);
                                                }
                                );
                },
          DajSveKlubove: function() {
                return $http.get('http://localhost:8080/klubovi')
                                .then(
                                                function(response){
                                                        return response.data;
                                                }, 
                                                function(errResponse){
                                                        console.error('Greska pri dobivanju klubova');
                                                        return $q.reject(errResponse);
                                                }
                                );
                },
          DajSveLige: function() {
                return $http.get('http://localhost:8080/lige')
                                .then(
                                                function(response){
                                                        return response.data;
                                                }, 
                                                function(errResponse){
                                                        console.error('Greska pri dobivanju liga');
                                                        return $q.reject(errResponse);
                                                }
                                );
                },
          DajSveSezone: function() {
                return $http.get('http://localhost:8080/sezone')
                                .then(
                                                function(response){
                                                        return response.data;
                                                }, 
                                                function(errResponse){
                                                        console.error('Greska pri dobivanju sezona');
                                                        return $q.reject(errResponse);
                                                }
                                );
                },
          createGrad: function(grad){
					return $http.post('http://localhost:8080/gradovi', grad)
							.then(
									function(response){
										return response.data;
									}, 
									function(errResponse){
										console.error('Greska pri stvaranju grada');
										return $q.reject(errResponse);
									}
							);
            },
          createKlub: function(klub){
					return $http.post('http://localhost:8080/klubovi', klub)
							.then(
									function(response){
										return response.data;
									}, 
									function(errResponse){
										console.error('Greska pri stvaranju kluba');
										return $q.reject(errResponse);
									}
							);
        },
          createLiga: function(liga){
                                        return $http.post('http://localhost:8080/lige', liga)
                                                        .then(
                                                                        function(response){
                                                                                return response.data;
                                                                        }, 
                                                                        function(errResponse){
                                                                                console.error('Greska pri stvaranju lige');
                                                                                return $q.reject(errResponse);
                                                                        }
                                                        );
          },
          createSezona: function(sezona){
                                        return $http.post('http://localhost:8080/sezone', sezona)
                                                        .then(
                                                                        function(response){
                                                                                return response.data;
                                                                        }, 
                                                                        function(errResponse){
                                                                                console.error('Greska pri stvaranju sezone');
                                                                                return $q.reject(errResponse);
                                                                        }
                                                        );
          },
          updateLiga: function(liga, id){
					return $http.put('http://localhost:8080/lige/'+id, liga)
							.then(
									function(response){
										return response.data;
									}, 
									function(errResponse){
										console.error('Error while updating user');
										return $q.reject(errResponse);
									}
							);
			},
          updateSezona: function(sezona, id){
              return $http.put('http://localhost:8080/sezone/'+id, sezona)
							.then(
									function(response){
										return response.data;
									}, 
									function(errResponse){
										console.error('Error while updating sezone');
										return $q.reject(errResponse);
									}
							);
          }
        };
}]);
