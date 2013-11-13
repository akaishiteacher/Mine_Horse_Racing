package net.akaishi_teacher.mhr.common;

/**
 * このクラスはDeserializeを任意のタイミングに呼び出すために、実装されたクラスです。<br>
 * このクラスを渡すことによって、Deserializeのタイミングに柔軟性をもたせます。
 * @author mozipi
 */
public interface Deserializer {

	/**
	 * This method will do deserializes.
	 */
	void deserializes();
	
}
