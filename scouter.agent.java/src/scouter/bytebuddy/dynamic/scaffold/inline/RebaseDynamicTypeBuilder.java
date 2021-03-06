// Generated by delombok at Sun Feb 26 12:31:38 KST 2017
package scouter.bytebuddy.dynamic.scaffold.inline;

import scouter.bytebuddy.ClassFileVersion;
import scouter.bytebuddy.asm.AsmVisitorWrapper;
import scouter.bytebuddy.description.method.MethodDescription;
import scouter.bytebuddy.description.method.MethodList;
import scouter.bytebuddy.description.type.TypeDescription;
import scouter.bytebuddy.dynamic.ClassFileLocator;
import scouter.bytebuddy.dynamic.DynamicType;
import scouter.bytebuddy.dynamic.TypeResolutionStrategy;

import scouter.bytebuddy.implementation.Implementation;
import scouter.bytebuddy.implementation.attribute.AnnotationRetention;
import scouter.bytebuddy.implementation.attribute.AnnotationValueFilter;
import scouter.bytebuddy.implementation.attribute.TypeAttributeAppender;
import scouter.bytebuddy.implementation.auxiliary.AuxiliaryType;
import scouter.bytebuddy.matcher.ElementMatcher;
import scouter.bytebuddy.matcher.LatentMatcher;
import scouter.bytebuddy.pool.TypePool;
import scouter.bytebuddy.dynamic.scaffold.*;
import scouter.bytebuddy.matcher.ElementMatchers;

import java.util.HashSet;
import java.util.Set;
import static scouter.bytebuddy.matcher.ElementMatchers.is;

/**
 * A type builder that rebases an instrumented type.
 *
 * @param <T> A loaded type that the dynamic type is guaranteed to be a subtype of.
 */
public class RebaseDynamicTypeBuilder<T> extends AbstractInliningDynamicTypeBuilder<T> {
    /**
     * The method rebase resolver to use for determining the name of a rebased method.
     */
    private final MethodNameTransformer methodNameTransformer;

    /**
     * Creates a rebase dynamic type builder.
     *
     * @param instrumentedType             An instrumented type representing the subclass.
     * @param classFileVersion             The class file version to use for types that are not based on an existing class file.
     * @param auxiliaryTypeNamingStrategy  The naming strategy to use for naming auxiliary types.
     * @param annotationValueFilterFactory The annotation value filter factory to use.
     * @param annotationRetention          The annotation retention strategy to use.
     * @param implementationContextFactory The implementation context factory to use.
     * @param methodGraphCompiler          The method graph compiler to use.
     * @param typeValidation               Determines if a type should be explicitly validated.
     * @param ignoredMethods               A matcher for identifying methods that should be excluded from instrumentation.
     * @param originalType                 The original type that is being redefined or rebased.
     * @param classFileLocator             The class file locator for locating the original type's class file.
     * @param methodNameTransformer        The method rebase resolver to use for determining the name of a rebased method.
     */
    public RebaseDynamicTypeBuilder(InstrumentedType.WithFlexibleName instrumentedType, ClassFileVersion classFileVersion, AuxiliaryType.NamingStrategy auxiliaryTypeNamingStrategy, AnnotationValueFilter.Factory annotationValueFilterFactory, AnnotationRetention annotationRetention, Implementation.Context.Factory implementationContextFactory, MethodGraph.Compiler methodGraphCompiler, TypeValidation typeValidation, LatentMatcher<? super MethodDescription> ignoredMethods, TypeDescription originalType, ClassFileLocator classFileLocator, MethodNameTransformer methodNameTransformer) {
        this(instrumentedType, new FieldRegistry.Default(), new MethodRegistry.Default(), annotationRetention.isEnabled() ? new TypeAttributeAppender.ForInstrumentedType.Differentiating(originalType) : TypeAttributeAppender.ForInstrumentedType.INSTANCE, AsmVisitorWrapper.NoOp.INSTANCE, classFileVersion, auxiliaryTypeNamingStrategy, annotationValueFilterFactory, annotationRetention, implementationContextFactory, methodGraphCompiler, typeValidation, ignoredMethods, originalType, classFileLocator, methodNameTransformer);
    }

    /**
     * Creates a rebase dynamic type builder.
     *
     * @param instrumentedType             An instrumented type representing the subclass.
     * @param fieldRegistry                The field pool to use.
     * @param methodRegistry               The method pool to use.
     * @param typeAttributeAppender        The type attribute appender to apply onto the instrumented type.
     * @param asmVisitorWrapper            The ASM visitor wrapper to apply onto the class writer.
     * @param classFileVersion             The class file version to use for types that are not based on an existing class file.
     * @param auxiliaryTypeNamingStrategy  The naming strategy to use for naming auxiliary types.
     * @param annotationValueFilterFactory The annotation value filter factory to use.
     * @param annotationRetention          The annotation retention strategy to use.
     * @param implementationContextFactory The implementation context factory to use.
     * @param methodGraphCompiler          The method graph compiler to use.
     * @param typeValidation               Determines if a type should be explicitly validated.
     * @param ignoredMethods               A matcher for identifying methods that should be excluded from instrumentation.
     * @param originalType                 The original type that is being redefined or rebased.
     * @param classFileLocator             The class file locator for locating the original type's class file.
     * @param methodNameTransformer        The method rebase resolver to use for determining the name of a rebased method.
     */
    protected RebaseDynamicTypeBuilder(InstrumentedType.WithFlexibleName instrumentedType, FieldRegistry fieldRegistry, MethodRegistry methodRegistry, TypeAttributeAppender typeAttributeAppender, AsmVisitorWrapper asmVisitorWrapper, ClassFileVersion classFileVersion, AuxiliaryType.NamingStrategy auxiliaryTypeNamingStrategy, AnnotationValueFilter.Factory annotationValueFilterFactory, AnnotationRetention annotationRetention, Implementation.Context.Factory implementationContextFactory, MethodGraph.Compiler methodGraphCompiler, TypeValidation typeValidation, LatentMatcher<? super MethodDescription> ignoredMethods, TypeDescription originalType, ClassFileLocator classFileLocator, MethodNameTransformer methodNameTransformer) {
        super(instrumentedType, fieldRegistry, methodRegistry, typeAttributeAppender, asmVisitorWrapper, classFileVersion, auxiliaryTypeNamingStrategy, annotationValueFilterFactory, annotationRetention, implementationContextFactory, methodGraphCompiler, typeValidation, ignoredMethods, originalType, classFileLocator);
        this.methodNameTransformer = methodNameTransformer;
    }

    @Override
    protected DynamicType.Builder<T> materialize(InstrumentedType.WithFlexibleName instrumentedType, FieldRegistry fieldRegistry, MethodRegistry methodRegistry, TypeAttributeAppender typeAttributeAppender, AsmVisitorWrapper asmVisitorWrapper, ClassFileVersion classFileVersion, AuxiliaryType.NamingStrategy auxiliaryTypeNamingStrategy, AnnotationValueFilter.Factory annotationValueFilterFactory, AnnotationRetention annotationRetention, Implementation.Context.Factory implementationContextFactory, MethodGraph.Compiler methodGraphCompiler, TypeValidation typeValidation, LatentMatcher<? super MethodDescription> ignoredMethods) {
        return new RebaseDynamicTypeBuilder<T>(instrumentedType, fieldRegistry, methodRegistry, typeAttributeAppender, asmVisitorWrapper, classFileVersion, auxiliaryTypeNamingStrategy, annotationValueFilterFactory, annotationRetention, implementationContextFactory, methodGraphCompiler, typeValidation, ignoredMethods, originalType, classFileLocator, methodNameTransformer);
    }

    @Override
    public DynamicType.Unloaded<T> make(TypeResolutionStrategy typeResolutionStrategy, TypePool typePool) {
        MethodRegistry.Prepared methodRegistry = this.methodRegistry.prepare(instrumentedType, methodGraphCompiler, typeValidation, InliningImplementationMatcher.of(ignoredMethods, originalType));
        MethodRebaseResolver methodRebaseResolver = MethodRebaseResolver.Default.make(methodRegistry.getInstrumentedType(), new HashSet<MethodDescription.Token>(originalType.getDeclaredMethods().filter(RebaseableMatcher.of(methodRegistry.getInstrumentedType(), methodRegistry.getInstrumentedMethods())).asTokenList(ElementMatchers.is(originalType))), classFileVersion, auxiliaryTypeNamingStrategy, methodNameTransformer);
        return TypeWriter.Default.<T>forRebasing(methodRegistry, fieldRegistry.compile(methodRegistry.getInstrumentedType()), typeAttributeAppender, asmVisitorWrapper, classFileVersion, annotationValueFilterFactory, annotationRetention, auxiliaryTypeNamingStrategy, implementationContextFactory, typeValidation, typePool, originalType, classFileLocator, methodRebaseResolver).make(typeResolutionStrategy.resolve());
    }


    /**
     * A matcher that filters any method that should not be rebased, i.e. that is not already defined by the original type.
     */
    protected static class RebaseableMatcher implements ElementMatcher<MethodDescription> {
        /**
         * The instrumented type.
         */
        private final TypeDescription instrumentedType;
        /**
         * A set of method tokens representing all instrumented methods.
         */
        private final Set<MethodDescription.Token> instrumentedMethodTokens;

        /**
         * Creates a new matcher for identifying rebasable methods.
         *
         * @param instrumentedType    The instrumented type.
         * @param instrumentedMethods A set of method tokens representing all instrumented methods.
         */
        protected RebaseableMatcher(TypeDescription instrumentedType, Set<MethodDescription.Token> instrumentedMethods) {
            this.instrumentedType = instrumentedType;
            this.instrumentedMethodTokens = instrumentedMethods;
        }

        /**
         * Returns a matcher that filters any method that should not be rebased.
         *
         * @param instrumentedType    The instrumented type.
         * @param instrumentedMethods All instrumented methods.
         * @return A suitable matcher that filters all methods that should not be rebased.
         */
        protected static ElementMatcher<MethodDescription> of(TypeDescription instrumentedType, MethodList<?> instrumentedMethods) {
            return new RebaseableMatcher(instrumentedType, new HashSet<MethodDescription.Token>(instrumentedMethods.asTokenList(ElementMatchers.is(instrumentedType))));
        }

        @Override
        public boolean matches(MethodDescription target) {
            return instrumentedMethodTokens.contains(target.asToken(ElementMatchers.is(instrumentedType)));
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        @javax.annotation.Generated("lombok")
        public boolean equals(final java.lang.Object o) {
            if (o == this) return true;
            if (!(o instanceof RebaseDynamicTypeBuilder.RebaseableMatcher)) return false;
            final RebaseDynamicTypeBuilder.RebaseableMatcher other = (RebaseDynamicTypeBuilder.RebaseableMatcher) o;
            if (!other.canEqual((java.lang.Object) this)) return false;
            final java.lang.Object this$instrumentedType = this.instrumentedType;
            final java.lang.Object other$instrumentedType = other.instrumentedType;
            if (this$instrumentedType == null ? other$instrumentedType != null : !this$instrumentedType.equals(other$instrumentedType)) return false;
            final java.lang.Object this$instrumentedMethodTokens = this.instrumentedMethodTokens;
            final java.lang.Object other$instrumentedMethodTokens = other.instrumentedMethodTokens;
            if (this$instrumentedMethodTokens == null ? other$instrumentedMethodTokens != null : !this$instrumentedMethodTokens.equals(other$instrumentedMethodTokens)) return false;
            return true;
        }

        @java.lang.SuppressWarnings("all")
        @javax.annotation.Generated("lombok")
        protected boolean canEqual(final java.lang.Object other) {
            return other instanceof RebaseDynamicTypeBuilder.RebaseableMatcher;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        @javax.annotation.Generated("lombok")
        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            final java.lang.Object $instrumentedType = this.instrumentedType;
            result = result * PRIME + ($instrumentedType == null ? 43 : $instrumentedType.hashCode());
            final java.lang.Object $instrumentedMethodTokens = this.instrumentedMethodTokens;
            result = result * PRIME + ($instrumentedMethodTokens == null ? 43 : $instrumentedMethodTokens.hashCode());
            return result;
        }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    @javax.annotation.Generated("lombok")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof RebaseDynamicTypeBuilder)) return false;
        final RebaseDynamicTypeBuilder<?> other = (RebaseDynamicTypeBuilder<?>) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$methodNameTransformer = this.methodNameTransformer;
        final java.lang.Object other$methodNameTransformer = other.methodNameTransformer;
        if (this$methodNameTransformer == null ? other$methodNameTransformer != null : !this$methodNameTransformer.equals(other$methodNameTransformer)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    @javax.annotation.Generated("lombok")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof RebaseDynamicTypeBuilder;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    @javax.annotation.Generated("lombok")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + super.hashCode();
        final java.lang.Object $methodNameTransformer = this.methodNameTransformer;
        result = result * PRIME + ($methodNameTransformer == null ? 43 : $methodNameTransformer.hashCode());
        return result;
    }
}
