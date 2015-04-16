# CoolReader
**Continuous Integration:** [![Circle CI](https://circleci.com/gh/soelynn/CoolReader.svg?style=shield)](https://circleci.com/gh/soelynn/CoolReader)

Simple and easy to use library to read csv file or other type of data source and have them instantiated into java Object without having to write CellProcessor like in SuperCSV. All in all, it's COOL!!!

# Movitation
jCSV is awesome. But, I was using it for writing test cases and I thought wouldn't it be cooler if I can just mention which csv file to load the data and instantiate the java Object without having to write the conversion code!!! This library also supports java Annotation. You can use annotations such as @CoolData, @CoolConstructor and @CoolConstructors.

# [Annotation] Example 1
```
public class ExampleClass {

	@CoolData(csvFileURI = "src/test/resources/CSV/PersonInfo.csv",
		     columns = {String.class, double.class, boolean.class,
		     		int.class, Country.class})
	Object[][] data;

	public ExampleClass() {
		CoolData.inject(this);
	}
	
	@DataProvider(name = "data")
	public Object[][] getData() { 
		return data;
	}
	
	@Test(dataProvider = "data")
	public void run(String name, double weight, boolean isMale, int height, Country country) {
		assert("Smith", name);
	}
}
```

# [Annotation] Example 2
```
public class ExampleClass {

	@CoolData(csvFileURI = "src/test/resources/CSV/PersonInfo.csv")
	@CoolConstructor(constructorClass = User.class, paramTypes = {String.class,
			double.class, boolean.class, int.class, Country.class})
	Object[][] data;

	public ExampleClass() {
		CoolData.inject(this);
	}
	
	@DataProvider(name = "data")
	public Object[][] getData() { 
		return data;
	}
	
	@Test(dataProvider = "data")
	public void run(User user) {
		assert("Soe Lynn", user.getName());
	}
}
```

# [Annotation] Example 3
```
public class ExampleClass {

	@CoolData(csvFileURI = "src/test/resources/CSV/PersonInfo.csv")
	@CoolConstructors({
			@CoolConstructor(constructorClass = User.class, paramTypes = {
					String.class, double.class, boolean.class, int.class,
					Country.class}),
			@CoolConstructor(constructorClass = User.class, paramTypes = {
					String.class, double.class, boolean.class, int.class,
					Country.class})})
	Object[][] data;

	public ExampleClass() {
		CoolData.inject(this);
	}
	
	@DataProvider(name = "data")
	public Object[][] getData() { 
		return data;
	}
	
	@Test(dataProvider = "data")
	public void run(User user1, User user2) {
		assert("Soe Lynn", user1.getName());
		assert("Xiao Zhu", user2.getName());
	}
}
```

# Example 2 (TestNG dataprovider):
```
@DataProvider(name = "data")
public Object[][] data() { 
	CoolReader csvReader = new CoolReader(csvFileURI);
	CoolConstructorParams userParams = new CoolConstructorParams(String.class, double.class,boolean.class, int.class, Country.class);
	return csvReader.readAllAsCustomObject(User.class, userParams);
}

@Test(dataProvider = "data")
public void run(User user) {
	assert("Soe Lynn", user.getName());
}

```
