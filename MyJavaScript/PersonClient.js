function usePerson() {
	// Create new instances of the Person class
	var person1 = new Person('Alice');
	var person2 = new Person('Bob');
	console.log("PersonClient: person1 is " + person1.firstName);
	console.log("PersonClient: person2 is " + person2.firstName);
	person1.sayHello();
	person2.sayHello();
	var student1 = new Student("Janet", "Physics");
	student1.sayHello();
	student1.dance();
	student1.sayGoodBye();
}
