use anyhow::anyhow;

fn main() {
    let str1 = "-17";
    let str2 = "Tux";

    //covert_to_int(str1);
    //covert_to_int(str2);

    //covert_to_int2(str1);
    //covert_to_int2(str2);

covert_to_int3(str1).unwrap();
    covert_to_int3(str2).unwrap_err();
}


fn covert_to_int(int: &str) -> () {
    let parsed_int = int.parse::<i32>();
    match parsed_int {
        Ok(int) => println!("Le carre de {:?} vaut : {}", parsed_int, int*int),
        Err(e) => println!("{} n'est pas un nombre entier", int),
    }
}

fn covert_to_int2(int: &str) -> () {
    let parsed_int = int.parse::<i32>();
    parsed_int.clone().expect("NJFKVFJKSHR");
    let integer: i32 = parsed_int.unwrap();
    println!("{}", integer*integer);

}

fn covert_to_int3(int: &str) -> anyhow::Result<()> {
    let value = int.parse::<i32>()?;
    println!("{}", value*value);
    Ok(())
}