# CoolReader
Simple and easy to use library to read csv file or other type of data source and have them instantiated into java Object without having to write CellProcessor like in SuperCSV. All in all, it's COOL!!!

# Movitation
jCSV is awesome. But, I was using it for writing test cases and I thought wouldn't it be cooler if I can just mention which csv file to load the data and instantiate the java Object without having to write the conversion code!!!

# Example
# Usecase 1:
```
CoolReader csvReader = new CoolReader(csvFileURI);
User user = (User) csvReader.readAllAsCustomObject(User.class,
				new CoolConstructorParams(String.class, double.class,
						boolean.class, int.class, Country.class))[0][0];
```

# Usecase 2:
```
Object[][] data = new Object[][]{
		new Object[]{"Soe Lynn", 54.5, true, 188, Country.MM}
};
CoolReader reader = new CoolReader(data);
User user = (User) csvReader.readAllAsCustomObject(User.class,
				new CoolConstructorParams(String.class, double.class,
						boolean.class, int.class, Country.class))[0][0];
```
