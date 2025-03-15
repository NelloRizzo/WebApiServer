package corso.java.webapi.services.loader;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JsonCity {
	private String nome;
	private String codice;
	private JsonCodeName zona;
	private JsonCodeName provincia;
	private JsonCodeName regione;
	private String sigla;
	private String codiceCatastale;
	private String[] cap;
	private long popolazione;
}
