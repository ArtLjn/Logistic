import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.sdk.abi.FunctionEncoder;
import org.fisco.bcos.sdk.abi.FunctionReturnDecoder;
import org.fisco.bcos.sdk.abi.TypeReference;
import org.fisco.bcos.sdk.abi.datatypes.Address;
import org.fisco.bcos.sdk.abi.datatypes.DynamicArray;
import org.fisco.bcos.sdk.abi.datatypes.Function;
import org.fisco.bcos.sdk.abi.datatypes.Type;
import org.fisco.bcos.sdk.abi.datatypes.Utf8String;
import org.fisco.bcos.sdk.abi.datatypes.generated.Int8;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.contract.Contract;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class NewEnergy extends Contract {
    public static final String[] BINARY_ARRAY = {"60806040523480156200001157600080fd5b50604051606080620022a6833981018060405262000033919081019062000196565b826000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555080600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555081600260006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555080600360006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555082600460006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505050506200020c565b60006200018e8251620001ec565b905092915050565b600080600060608486031215620001ac57600080fd5b6000620001bc8682870162000180565b9350506020620001cf8682870162000180565b9250506040620001e28682870162000180565b9150509250925092565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b61208a806200021c6000396000f300608060405260043610610154576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680630b2d8924146101595780630c04edf514610196578063176edb4e146101d357806317a2e2fa1461021057806323f52aad1461024d57806327ef31581461028a5780632c816786146102c75780632e92e6bc14610304578063335bd21e1461034157806340b2d3af1461036c5780634e129ce1146103a957806359d27526146103d457806365745470146103ff578063925fb18a1461042b578063930e3f3e1461046857806396f640551461049357806397ad36e2146104d057806397cedbee146104fb578063ae7ac5b314610538578063aeca20ff14610563578063bc9253df1461058e578063bd3a2c4a146105cb578063c0ff9bb714610608578063d9649df614610633578063ebe6ccd514610670578063fbd01893146106ad575b600080fd5b34801561016557600080fd5b50610180600480360361017b9190810190611b12565b6106d8565b60405161018d9190611dec565b60405180910390f35b3480156101a257600080fd5b506101bd60048036036101b89190810190611b12565b6107aa565b6040516101ca9190611dec565b60405180910390f35b3480156101df57600080fd5b506101fa60048036036101f59190810190611b12565b61087b565b6040516102079190611dec565b60405180910390f35b34801561021c57600080fd5b5061023760048036036102329190810190611b12565b61094d565b6040516102449190611dec565b60405180910390f35b34801561025957600080fd5b50610274600480360361026f9190810190611b12565b610a1e565b6040516102819190611e07565b60405180910390f35b34801561029657600080fd5b506102b160048036036102ac9190810190611b12565b610af5565b6040516102be9190611dec565b60405180910390f35b3480156102d357600080fd5b506102ee60048036036102e99190810190611b12565b610bc7565b6040516102fb9190611e07565b60405180910390f35b34801561031057600080fd5b5061032b60048036036103269190810190611b12565b610c9e565b6040516103389190611dec565b60405180910390f35b34801561034d57600080fd5b50610356610d70565b6040516103639190611dd1565b60405180910390f35b34801561037857600080fd5b50610393600480360361038e9190810190611b12565b610d95565b6040516103a09190611dec565b60405180910390f35b3480156103b557600080fd5b506103be610e67565b6040516103cb9190611db6565b60405180910390f35b3480156103e057600080fd5b506103e9610e8d565b6040516103f69190611d27565b60405180910390f35b34801561040b57600080fd5b50610414610f57565b604051610422929190611d49565b60405180910390f35b34801561043757600080fd5b50610452600480360361044d9190810190611b12565b611025565b60405161045f9190611dec565b60405180910390f35b34801561047457600080fd5b5061047d6110f6565b60405161048a9190611dd1565b60405180910390f35b34801561049f57600080fd5b506104ba60048036036104b59190810190611b12565b61111c565b6040516104c79190611dec565b60405180910390f35b3480156104dc57600080fd5b506104e56111ed565b6040516104f29190611e07565b60405180910390f35b34801561050757600080fd5b50610522600480360361051d9190810190611b12565b6112b6565b60405161052f9190611dec565b60405180910390f35b34801561054457600080fd5b5061054d611388565b60405161055a9190611e07565b60405180910390f35b34801561056f57600080fd5b50610578611452565b6040516105859190611d80565b60405180910390f35b34801561059a57600080fd5b506105b560048036036105b09190810190611b12565b611478565b6040516105c29190611dec565b60405180910390f35b3480156105d757600080fd5b506105f260048036036105ed9190810190611b12565b61154a565b6040516105ff9190611dec565b60405180910390f35b34801561061457600080fd5b5061061d61161c565b60405161062a9190611e07565b60405180910390f35b34801561063f57600080fd5b5061065a60048036036106559190810190611b12565b6116e6565b6040516106679190611dec565b60405180910390f35b34801561067c57600080fd5b5061069760048036036106929190810190611b12565b6117b8565b6040516106a49190611dec565b60405180910390f35b3480156106b957600080fd5b506106c261188a565b6040516106cf9190611d9b565b60405180910390f35b6000600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16633a7d280c836040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004016107519190611e07565b602060405180830381600087803b15801561076b57600080fd5b505af115801561077f573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052506107a39190810190611ae9565b9050919050565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663461fe9e3836040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004016108229190611e07565b602060405180830381600087803b15801561083c57600080fd5b505af1158015610850573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052506108749190810190611ae9565b9050919050565b6000600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166380599e4b836040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004016108f49190611e07565b602060405180830381600087803b15801561090e57600080fd5b505af1158015610922573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052506109469190810190611ae9565b9050919050565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166380599e4b836040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004016109c59190611e07565b602060405180830381600087803b1580156109df57600080fd5b505af11580156109f3573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250610a179190810190611ae9565b9050919050565b6060600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16633ef51aa3836040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401610a979190611e5e565b600060405180830381600087803b158015610ab157600080fd5b505af1158015610ac5573d6000803e3d6000fd5b505050506040513d6000823e3d601f19601f82011682018060405250610aee9190810190611b53565b9050919050565b6000600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16634e47bf37836040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401610b6e9190611e07565b602060405180830381600087803b158015610b8857600080fd5b505af1158015610b9c573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250610bc09190810190611ae9565b9050919050565b6060600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16633ef51aa3836040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401610c409190611e29565b600060405180830381600087803b158015610c5a57600080fd5b505af1158015610c6e573d6000803e3d6000fd5b505050506040513d6000823e3d601f19601f82011682018060405250610c979190810190611b53565b9050919050565b6000600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166380599e4b836040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401610d179190611e07565b602060405180830381600087803b158015610d3157600080fd5b505af1158015610d45573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250610d699190810190611ae9565b9050919050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6000600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663b1498e29836040518263ffffffff167c0100","000000000000000000000000000000000000000000000000000000028152600401610e0e9190611e07565b602060405180830381600087803b158015610e2857600080fd5b505af1158015610e3c573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250610e609190810190611ae9565b9050919050565b600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6060600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16635398a96b6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401600060405180830381600087803b158015610f1557600080fd5b505af1158015610f29573d6000803e3d6000fd5b505050506040513d6000823e3d601f19601f82011682018060405250610f529190810190611a3c565b905090565b606080600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663dbf5755a6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401600060405180830381600087803b158015610fe057600080fd5b505af1158015610ff4573d6000803e3d6000fd5b505050506040513d6000823e3d601f19601f8201168201806040525061101d9190810190611a7d565b915091509091565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663b1498e29836040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040161109d9190611e07565b602060405180830381600087803b1580156110b757600080fd5b505af11580156110cb573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052506110ef9190810190611ae9565b9050919050565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16633d7403a3836040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004016111949190611e07565b602060405180830381600087803b1580156111ae57600080fd5b505af11580156111c2573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052506111e69190810190611ae9565b9050919050565b60606000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166315b529e06040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401600060405180830381600087803b15801561127457600080fd5b505af1158015611288573d6000803e3d6000fd5b505050506040513d6000823e3d601f19601f820116820180604052506112b19190810190611b53565b905090565b6000600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16633d7403a3836040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040161132f9190611e07565b602060405180830381600087803b15801561134957600080fd5b505af115801561135d573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052506113819190810190611ae9565b9050919050565b6060600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166315b529e06040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401600060405180830381600087803b15801561141057600080fd5b505af1158015611424573d6000803e3d6000fd5b505050506040513d6000823e3d601f19601f8201168201806040525061144d9190810190611b53565b905090565b600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6000600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663b1498e29836040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004016114f19190611e07565b602060405180830381600087803b15801561150b57600080fd5b505af115801561151f573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052506115439190810190611ae9565b9050919050565b6000600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663a0258d0b836040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004016115c39190611e07565b602060405180830381600087803b1580156115dd57600080fd5b505af11580156115f1573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052506116159190810190611ae9565b9050919050565b6060600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16639990404d6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401600060405180830381600087803b1580156116a457600080fd5b505af11580156116b8573d6000803e3d6000fd5b505050506040513d6000823e3d601f19601f820116820180604052506116e19190810190611b53565b905090565b6000600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663461fe9e3836040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040161175f9190611e07565b602060405180830381600087803b15801561177957600080fd5b505af115801561178d573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052506117b19190810190611ae9565b9050919050565b6000600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16633d7403a3836040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004016118319190611e07565b602060405180830381600087803b15801561184b57600080fd5b505af115801561185f573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052506118839190810190611ae9565b9050919050565b600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b600082601f83011215156118c357600080fd5b81516118d66118d182611ec0565b611e93565b9150818183526020840193506020810190508360005b8381101561191c5781518601611902888261193a565b8452602084019350602083019250506001810190506118ec565b5050505092915050565b60006119328251611fa8565b905092915050565b600082601f830112151561194d57600080fd5b815161196061195b82611ee8565b611e93565b9150808252602083016020830185838301111561197c57600080fd5b61198783828461200c565b50505092915050565b600082601f83011215156119a357600080fd5b81356119b66119b182611f14565b611e93565b915080825260208301602083018583830111156119d257600080fd5b6119dd838284611ffd565b50505092915050565b600082601f83011215156119f957600080fd5b8151611a0c611a0782611f14565b611e93565b91508082526020830160208301858383011115611a2857600080fd5b611a3383828461200c565b50505092915050565b600060208284031215611a4e57600080fd5b600082015167ffffffffffffffff811115611a6857600080fd5b611a74848285016118b0565b91505092915050565b60008060408385031215611a9057600080fd5b600083015167ffffffffffffffff811115611aaa57600080fd5b611ab6858286016118b0565b925050602083015167ffffffffffffffff811115611ad357600080fd5b611adf858286016118b0565b9150509250929050565b600060208284031215611afb57600080fd5b6000611b0984828501611926565b91505092915050565b600060208284031215611b2457600080fd5b600082013567ffffffffffffffff811115611b3e57600080fd5b611b4a84828501611990565b91505092915050565b600060208284031215611b6557600080fd5b600082015167ffffffffffffffff811115611b7f57600080fd5b611b8b848285016119e6565b91505092915050565b6000611b9f82611f4d565b80845260208401935083602082028501611bb885611f40565b60005b84811015611bf1578383038852611bd3838351611c83565b9250611bde82611f6e565b9150602088019750600181019050611bbb565b508196508694505050505092915050565b611c0b81611fb5565b82525050565b611c1a81611fc7565b82525050565b611c2981611fd9565b82525050565b611c3881611feb565b82525050565b611c4781611f9b565b82525050565b6000611c5882611f63565b808452611c6c81602086016020860161200c565b611c758161203f565b602085010191505092915050565b6000611c8e82611f58565b808452611ca281602086016020860161200c565b611cab8161203f565b602085010191505092915050565b6000600a82527f4f776e65725f73686970000000000000000000000000000000000000000000006020830152604082019050919050565b6000600682527f6e756d5f696400000000000000000000000000000000000000000000000000006020830152604082019050919050565b60006020820190508181036000830152611d418184611b94565b905092915050565b60006040820190508181036000830152611d638185611b94565b90508181036020830152611d778184611b94565b90509392505050565b6000602082019050611d956000830184611c02565b92915050565b6000602082019050611db06000830184611c11565b92915050565b6000602082019050611dcb6000830184611c20565b92915050565b6000602082019050611de66000830184611c","2f565b92915050565b6000602082019050611e016000830184611c3e565b92915050565b60006020820190508181036000830152611e218184611c4d565b905092915050565b60006040820190508181036000830152611e4281611cb9565b90508181036020830152611e568184611c4d565b905092915050565b60006040820190508181036000830152611e7781611cf0565b90508181036020830152611e8b8184611c4d565b905092915050565b6000604051905081810181811067ffffffffffffffff82111715611eb657600080fd5b8060405250919050565b600067ffffffffffffffff821115611ed757600080fd5b602082029050602081019050919050565b600067ffffffffffffffff821115611eff57600080fd5b601f19601f8301169050602081019050919050565b600067ffffffffffffffff821115611f2b57600080fd5b601f19601f8301169050602081019050919050565b6000602082019050919050565b600081519050919050565b600081519050919050565b600081519050919050565b6000602082019050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b60008160000b9050919050565b60008160000b9050919050565b6000611fc082611f7b565b9050919050565b6000611fd282611f7b565b9050919050565b6000611fe482611f7b565b9050919050565b6000611ff682611f7b565b9050919050565b82818337600083830152505050565b60005b8381101561202a57808201518184015260208101905061200f565b83811115612039576000848401525b50505050565b6000601f19601f83011690509190505600a265627a7a72305820711784c2743fb545cfbbc6cadb777e0d931c613d3cac18592a002081b3b9dff26c6578706572696d656e74616cf50037"};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"constant\":false,\"inputs\":[{\"name\":\"_fields\",\"type\":\"string\"}],\"name\":\"Login\",\"outputs\":[{\"name\":\"\",\"type\":\"int8\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_fields\",\"type\":\"string\"}],\"name\":\"Energy_Order_insert\",\"outputs\":[{\"name\":\"\",\"type\":\"int8\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_numId\",\"type\":\"string\"}],\"name\":\"SPU_Remove\",\"outputs\":[{\"name\":\"\",\"type\":\"int8\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_numId\",\"type\":\"string\"}],\"name\":\"Energy_Remove\",\"outputs\":[{\"name\":\"\",\"type\":\"int8\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"numId\",\"type\":\"string\"}],\"name\":\"SPU_QueryByID\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_fields\",\"type\":\"string\"}],\"name\":\"SPU_Seller\",\"outputs\":[{\"name\":\"\",\"type\":\"int8\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_ownerShip\",\"type\":\"string\"}],\"name\":\"SPU_QueryByAddr\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_address\",\"type\":\"string\"}],\"name\":\"AccountRemove\",\"outputs\":[{\"name\":\"\",\"type\":\"int8\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"energyInterface\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_fields\",\"type\":\"string\"}],\"name\":\"Register\",\"outputs\":[{\"name\":\"\",\"type\":\"int8\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"roleInterface\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"Energy_Select\",\"outputs\":[{\"name\":\"\",\"type\":\"string[]\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"QueryAllAccount\",\"outputs\":[{\"name\":\"\",\"type\":\"string[]\"},{\"name\":\"\",\"type\":\"string[]\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_numId\",\"type\":\"string\"}],\"name\":\"Energy_Init\",\"outputs\":[{\"name\":\"\",\"type\":\"int8\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"equipInterface\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_fields\",\"type\":\"string\"}],\"name\":\"Energy_Update\",\"outputs\":[{\"name\":\"\",\"type\":\"int8\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"Energy_Order_get\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_fields\",\"type\":\"string\"}],\"name\":\"AccountUpdate\",\"outputs\":[{\"name\":\"\",\"type\":\"int8\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"SPU_Query\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"energyInterfaceTwo\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_fields\",\"type\":\"string\"}],\"name\":\"SPU_Insert\",\"outputs\":[{\"name\":\"\",\"type\":\"int8\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_fields\",\"type\":\"string\"}],\"name\":\"SPU_Transfer\",\"outputs\":[{\"name\":\"\",\"type\":\"int8\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"SPU_Order_Query\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_fields\",\"type\":\"string\"}],\"name\":\"SPU_Order_Insert\",\"outputs\":[{\"name\":\"\",\"type\":\"int8\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_fields\",\"type\":\"string\"}],\"name\":\"SPU_Update\",\"outputs\":[{\"name\":\"\",\"type\":\"int8\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"equipInterfaceTwo\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"name\":\"energy\",\"type\":\"address\"},{\"name\":\"role\",\"type\":\"address\"},{\"name\":\"equip\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_LOGIN = "Login";

    public static final String FUNC_ENERGY_ORDER_INSERT = "Energy_Order_insert";

    public static final String FUNC_SPU_REMOVE = "SPU_Remove";

    public static final String FUNC_ENERGY_REMOVE = "Energy_Remove";

    public static final String FUNC_SPU_QUERYBYID = "SPU_QueryByID";

    public static final String FUNC_SPU_SELLER = "SPU_Seller";

    public static final String FUNC_SPU_QUERYBYADDR = "SPU_QueryByAddr";

    public static final String FUNC_ACCOUNTREMOVE = "AccountRemove";

    public static final String FUNC_ENERGYINTERFACE = "energyInterface";

    public static final String FUNC_REGISTER = "Register";

    public static final String FUNC_ROLEINTERFACE = "roleInterface";

    public static final String FUNC_ENERGY_SELECT = "Energy_Select";

    public static final String FUNC_QUERYALLACCOUNT = "QueryAllAccount";

    public static final String FUNC_ENERGY_INIT = "Energy_Init";

    public static final String FUNC_EQUIPINTERFACE = "equipInterface";

    public static final String FUNC_ENERGY_UPDATE = "Energy_Update";

    public static final String FUNC_ENERGY_ORDER_GET = "Energy_Order_get";

    public static final String FUNC_ACCOUNTUPDATE = "AccountUpdate";

    public static final String FUNC_SPU_QUERY = "SPU_Query";

    public static final String FUNC_ENERGYINTERFACETWO = "energyInterfaceTwo";

    public static final String FUNC_SPU_INSERT = "SPU_Insert";

    public static final String FUNC_SPU_TRANSFER = "SPU_Transfer";

    public static final String FUNC_SPU_ORDER_QUERY = "SPU_Order_Query";

    public static final String FUNC_SPU_ORDER_INSERT = "SPU_Order_Insert";

    public static final String FUNC_SPU_UPDATE = "SPU_Update";

    public static final String FUNC_EQUIPINTERFACETWO = "equipInterfaceTwo";

    protected NewEnergy(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public TransactionReceipt Login(String _fields) {
        final Function function = new Function(
                FUNC_LOGIN, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_fields)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] Login(String _fields, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_LOGIN, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_fields)), 
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForLogin(String _fields) {
        final Function function = new Function(
                FUNC_LOGIN, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_fields)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<String> getLoginInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_LOGIN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
                );
    }

    public Tuple1<BigInteger> getLoginOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_LOGIN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int8>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public TransactionReceipt Energy_Order_insert(String _fields) {
        final Function function = new Function(
                FUNC_ENERGY_ORDER_INSERT, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_fields)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] Energy_Order_insert(String _fields, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_ENERGY_ORDER_INSERT, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_fields)), 
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForEnergy_Order_insert(String _fields) {
        final Function function = new Function(
                FUNC_ENERGY_ORDER_INSERT, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_fields)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<String> getEnergy_Order_insertInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_ENERGY_ORDER_INSERT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
                );
    }

    public Tuple1<BigInteger> getEnergy_Order_insertOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_ENERGY_ORDER_INSERT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int8>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public TransactionReceipt SPU_Remove(String _numId) {
        final Function function = new Function(
                FUNC_SPU_REMOVE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_numId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] SPU_Remove(String _numId, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_SPU_REMOVE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_numId)), 
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForSPU_Remove(String _numId) {
        final Function function = new Function(
                FUNC_SPU_REMOVE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_numId)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<String> getSPU_RemoveInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_SPU_REMOVE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
                );
    }

    public Tuple1<BigInteger> getSPU_RemoveOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_SPU_REMOVE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int8>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public TransactionReceipt Energy_Remove(String _numId) {
        final Function function = new Function(
                FUNC_ENERGY_REMOVE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_numId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] Energy_Remove(String _numId, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_ENERGY_REMOVE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_numId)), 
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForEnergy_Remove(String _numId) {
        final Function function = new Function(
                FUNC_ENERGY_REMOVE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_numId)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<String> getEnergy_RemoveInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_ENERGY_REMOVE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
                );
    }

    public Tuple1<BigInteger> getEnergy_RemoveOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_ENERGY_REMOVE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int8>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public String SPU_QueryByID(String numId) throws ContractException {
        final Function function = new Function(FUNC_SPU_QUERYBYID, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(numId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public TransactionReceipt SPU_Seller(String _fields) {
        final Function function = new Function(
                FUNC_SPU_SELLER, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_fields)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] SPU_Seller(String _fields, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_SPU_SELLER, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_fields)), 
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForSPU_Seller(String _fields) {
        final Function function = new Function(
                FUNC_SPU_SELLER, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_fields)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<String> getSPU_SellerInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_SPU_SELLER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
                );
    }

    public Tuple1<BigInteger> getSPU_SellerOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_SPU_SELLER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int8>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public String SPU_QueryByAddr(String _ownerShip) throws ContractException {
        final Function function = new Function(FUNC_SPU_QUERYBYADDR, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_ownerShip)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public TransactionReceipt AccountRemove(String _address) {
        final Function function = new Function(
                FUNC_ACCOUNTREMOVE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_address)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] AccountRemove(String _address, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_ACCOUNTREMOVE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_address)), 
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForAccountRemove(String _address) {
        final Function function = new Function(
                FUNC_ACCOUNTREMOVE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_address)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<String> getAccountRemoveInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_ACCOUNTREMOVE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
                );
    }

    public Tuple1<BigInteger> getAccountRemoveOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_ACCOUNTREMOVE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int8>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public String energyInterface() throws ContractException {
        final Function function = new Function(FUNC_ENERGYINTERFACE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public TransactionReceipt Register(String _fields) {
        final Function function = new Function(
                FUNC_REGISTER, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_fields)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] Register(String _fields, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_REGISTER, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_fields)), 
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForRegister(String _fields) {
        final Function function = new Function(
                FUNC_REGISTER, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_fields)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<String> getRegisterInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_REGISTER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
                );
    }

    public Tuple1<BigInteger> getRegisterOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_REGISTER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int8>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public String roleInterface() throws ContractException {
        final Function function = new Function(FUNC_ROLEINTERFACE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public List Energy_Select() throws ContractException {
        final Function function = new Function(FUNC_ENERGY_SELECT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Utf8String>>() {}));
        List<Type> result = (List<Type>) executeCallWithSingleValueReturn(function, List.class);
        return convertToNative(result);
    }

    public Tuple2<List<String>, List<String>> QueryAllAccount() throws ContractException {
        final Function function = new Function(FUNC_QUERYALLACCOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Utf8String>>() {}, new TypeReference<DynamicArray<Utf8String>>() {}));
        List<Type> results = executeCallWithMultipleValueReturn(function);
        return new Tuple2<List<String>, List<String>>(
                convertToNative((List<Utf8String>) results.get(0).getValue()), 
                convertToNative((List<Utf8String>) results.get(1).getValue()));
    }

    public TransactionReceipt Energy_Init(String _numId) {
        final Function function = new Function(
                FUNC_ENERGY_INIT, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_numId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] Energy_Init(String _numId, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_ENERGY_INIT, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_numId)), 
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForEnergy_Init(String _numId) {
        final Function function = new Function(
                FUNC_ENERGY_INIT, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_numId)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<String> getEnergy_InitInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_ENERGY_INIT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
                );
    }

    public Tuple1<BigInteger> getEnergy_InitOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_ENERGY_INIT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int8>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public String equipInterface() throws ContractException {
        final Function function = new Function(FUNC_EQUIPINTERFACE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public TransactionReceipt Energy_Update(String _fields) {
        final Function function = new Function(
                FUNC_ENERGY_UPDATE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_fields)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] Energy_Update(String _fields, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_ENERGY_UPDATE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_fields)), 
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForEnergy_Update(String _fields) {
        final Function function = new Function(
                FUNC_ENERGY_UPDATE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_fields)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<String> getEnergy_UpdateInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_ENERGY_UPDATE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
                );
    }

    public Tuple1<BigInteger> getEnergy_UpdateOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_ENERGY_UPDATE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int8>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public String Energy_Order_get() throws ContractException {
        final Function function = new Function(FUNC_ENERGY_ORDER_GET, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public TransactionReceipt AccountUpdate(String _fields) {
        final Function function = new Function(
                FUNC_ACCOUNTUPDATE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_fields)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] AccountUpdate(String _fields, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_ACCOUNTUPDATE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_fields)), 
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForAccountUpdate(String _fields) {
        final Function function = new Function(
                FUNC_ACCOUNTUPDATE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_fields)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<String> getAccountUpdateInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_ACCOUNTUPDATE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
                );
    }

    public Tuple1<BigInteger> getAccountUpdateOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_ACCOUNTUPDATE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int8>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public String SPU_Query() throws ContractException {
        final Function function = new Function(FUNC_SPU_QUERY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public String energyInterfaceTwo() throws ContractException {
        final Function function = new Function(FUNC_ENERGYINTERFACETWO, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public TransactionReceipt SPU_Insert(String _fields) {
        final Function function = new Function(
                FUNC_SPU_INSERT, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_fields)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] SPU_Insert(String _fields, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_SPU_INSERT, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_fields)), 
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForSPU_Insert(String _fields) {
        final Function function = new Function(
                FUNC_SPU_INSERT, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_fields)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<String> getSPU_InsertInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_SPU_INSERT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
                );
    }

    public Tuple1<BigInteger> getSPU_InsertOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_SPU_INSERT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int8>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public TransactionReceipt SPU_Transfer(String _fields) {
        final Function function = new Function(
                FUNC_SPU_TRANSFER, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_fields)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] SPU_Transfer(String _fields, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_SPU_TRANSFER, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_fields)), 
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForSPU_Transfer(String _fields) {
        final Function function = new Function(
                FUNC_SPU_TRANSFER, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_fields)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<String> getSPU_TransferInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_SPU_TRANSFER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
                );
    }

    public Tuple1<BigInteger> getSPU_TransferOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_SPU_TRANSFER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int8>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public String SPU_Order_Query() throws ContractException {
        final Function function = new Function(FUNC_SPU_ORDER_QUERY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public TransactionReceipt SPU_Order_Insert(String _fields) {
        final Function function = new Function(
                FUNC_SPU_ORDER_INSERT, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_fields)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] SPU_Order_Insert(String _fields, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_SPU_ORDER_INSERT, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_fields)), 
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForSPU_Order_Insert(String _fields) {
        final Function function = new Function(
                FUNC_SPU_ORDER_INSERT, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_fields)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<String> getSPU_Order_InsertInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_SPU_ORDER_INSERT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
                );
    }

    public Tuple1<BigInteger> getSPU_Order_InsertOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_SPU_ORDER_INSERT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int8>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public TransactionReceipt SPU_Update(String _fields) {
        final Function function = new Function(
                FUNC_SPU_UPDATE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_fields)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] SPU_Update(String _fields, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_SPU_UPDATE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_fields)), 
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForSPU_Update(String _fields) {
        final Function function = new Function(
                FUNC_SPU_UPDATE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_fields)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<String> getSPU_UpdateInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_SPU_UPDATE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
                );
    }

    public Tuple1<BigInteger> getSPU_UpdateOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_SPU_UPDATE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int8>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public String equipInterfaceTwo() throws ContractException {
        final Function function = new Function(FUNC_EQUIPINTERFACETWO, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public static NewEnergy load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new NewEnergy(contractAddress, client, credential);
    }

    public static NewEnergy deploy(Client client, CryptoKeyPair credential, String energy, String role, String equip) throws ContractException {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(energy), 
                new org.fisco.bcos.sdk.abi.datatypes.Address(role), 
                new org.fisco.bcos.sdk.abi.datatypes.Address(equip)));
        return deploy(NewEnergy.class, client, credential, getBinary(client.getCryptoSuite()), encodedConstructor);
    }
}
